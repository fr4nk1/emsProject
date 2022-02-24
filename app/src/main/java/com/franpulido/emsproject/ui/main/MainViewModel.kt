package com.franpulido.emsproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData
import com.franpulido.emsproject.models.HistoricalEmsModel
import com.franpulido.emsproject.models.HistoricalEmsModelList
import com.franpulido.emsproject.ui.common.ScopedViewModel
import com.franpulido.emsproject.ui.common.colorsTheme
import com.franpulido.emsproject.ui.common.round
import com.franpulido.usecases.GetHistoricalData
import com.franpulido.usecases.GetLiveData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getHistoricalData: GetHistoricalData,
    private val getLiveData: GetLiveData
) :
    ScopedViewModel() {

    private lateinit var historicalData: List<HistoricalEmsData>
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val listElements = arrayOf("Solar Power", "Quasar Power", "Grid Power")

    sealed class UiModel {
        object LoadingShow : UiModel()
        object LoadingHide : UiModel()
        object DashboardShow : UiModel()
        object DashboardHide : UiModel()
        object Error : UiModel()
        object Init : UiModel()
        class ContentLiveData(val liveData: LiveEmsData) : UiModel()
        class ContentStatistics(val statistics: LiveEmsData, val dataSetStatistics: PieDataSet) :
            UiModel()

        class NavigationToDetail(val historicalData: HistoricalEmsModelList) : UiModel()
        class ShowQuasarCharged(val totalQuasarCharged: Double) : MainViewModel.UiModel()
        class ShowQuasarDischarged(val totalQuasarDischarged: Double) : MainViewModel.UiModel()
    }

    private fun refresh() {
        _model.postValue(UiModel.Init)
    }

    fun initUi() {
        launch {
            _model.value = UiModel.LoadingShow
            _model.value = UiModel.DashboardHide
            historicalData = getHistoricalData.invoke()

            if (historicalData.isEmpty()) {
                _model.value = UiModel.Error
            } else {
                val quasarChargedList =
                    historicalData.filter { it.quasarsActivePower > 0 }.map { it.quasarsActivePower }
                val quasarDisChargedList =
                    historicalData.filter { it.quasarsActivePower < 0 }.map { it.quasarsActivePower }
                _model.value = UiModel.ShowQuasarCharged(quasarChargedList.sumOf { it }.round())
                _model.value =
                    UiModel.ShowQuasarDischarged(abs(quasarDisChargedList.sumOf { it }.round()))

                val liveEmsData = getLiveData.invoke()
                val formatLiveEmsData = LiveEmsData(
                    liveEmsData.solarPower.round(),
                    liveEmsData.quasarsPower.round(),
                    liveEmsData.gridPower.round(),
                    liveEmsData.buildingDemand.round(),
                    liveEmsData.systemSoc,
                    liveEmsData.totalEnergy,
                    liveEmsData.currentEnergy
                )
                _model.value = UiModel.ContentLiveData(formatLiveEmsData)

                val formatStatisticsData = LiveEmsData(
                    (liveEmsData.solarPower * 100 / liveEmsData.buildingDemand).round(),
                    abs(liveEmsData.quasarsPower * 100 / liveEmsData.buildingDemand).round(),
                    (liveEmsData.gridPower.round() * 100 / liveEmsData.buildingDemand).round(),
                    liveEmsData.buildingDemand.round(),
                    liveEmsData.systemSoc,
                    liveEmsData.totalEnergy,
                    liveEmsData.currentEnergy
                )
                _model.value = UiModel.ContentStatistics(
                    formatStatisticsData,
                    getDataSetStatistics(formatStatisticsData)
                )
                _model.value = UiModel.DashboardShow
            }

            _model.value = UiModel.LoadingHide
        }
    }

    fun onViewDetails() {
        val newList: List<HistoricalEmsModel> = mapperHistoricalEmsModel()
        val data = HistoricalEmsModelList(newList.takeLast(20))
        _model.value = UiModel.NavigationToDetail(data)
    }

    fun onViewAllDetails() {
        val newList: List<HistoricalEmsModel> = mapperHistoricalEmsModel()
        val data = HistoricalEmsModelList(newList)
        _model.value = UiModel.NavigationToDetail(data)
    }

    private fun getDataSetStatistics(formatStatisticsData: LiveEmsData): PieDataSet {
        val entries = ArrayList<PieEntry>()

        with(formatStatisticsData) {
            entries.add(PieEntry(solarPower.toFloat(), listElements[0]))
            entries.add(PieEntry(quasarsPower.toFloat(), listElements[1]))
            entries.add(PieEntry(gridPower.toFloat(), listElements[2]))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val colors = ArrayList<Int>()
        for (c in colorsTheme) colors.add(c)
        dataSet.colors = colors

        return dataSet
    }

    private fun mapperHistoricalEmsModel(): List<HistoricalEmsModel> = historicalData.map {
        HistoricalEmsModel(
            it.buildingActivePower,
            it.gridActivePower,
            it.pvActivePower,
            it.quasarsActivePower,
            it.timeStamp
        )
    }

}