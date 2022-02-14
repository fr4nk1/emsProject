package com.franpulido.emsproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData
import com.franpulido.emsproject.ui.common.ScopedViewModel
import com.franpulido.emsproject.ui.common.round
import com.franpulido.usecases.GetHistoricalData
import com.franpulido.usecases.GetLiveData
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

    sealed class UiModel {
        object LoadingShow : UiModel()
        object LoadingHide : UiModel()
        object DashboardShow : UiModel()
        object DashboardHide : UiModel()
        object Error : UiModel()
        object Init : UiModel()
        class ContentLiveData(val liveData: LiveEmsData) : UiModel()
        class ContentStatistics(val statistics: LiveEmsData) : UiModel()
        class NavigationToDetail(val retrieveAllInfo: Boolean) : UiModel()
        class ShowQuasarCharged(val totalQuasarCharged: Double) : MainViewModel.UiModel()
        class ShowQuasarDischarged(val totalQuasarDischarged: Double) : MainViewModel.UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.Init
    }

    fun initUi() {
        launch {
            _model.value = UiModel.LoadingShow
            _model.value = UiModel.DashboardHide
            historicalData = getHistoricalData.invoke()
            val quasarChargedList =
                historicalData.filter { it.quasarsActivePower > 0 }.map { it.quasarsActivePower }
            val quasarDisChargedList =
                historicalData.filter { it.quasarsActivePower < 0 }.map { it.quasarsActivePower }

            if (historicalData.isEmpty()) {
                _model.value = UiModel.Error
            } else {
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
                    (liveEmsData.solarPower*100/liveEmsData.buildingDemand).round(),
                    abs(liveEmsData.quasarsPower*100/liveEmsData.buildingDemand).round(),
                    (liveEmsData.gridPower.round()*100/liveEmsData.buildingDemand).round(),
                    liveEmsData.buildingDemand.round(),
                    liveEmsData.systemSoc,
                    liveEmsData.totalEnergy,
                    liveEmsData.currentEnergy
                )
                _model.value = UiModel.ContentStatistics(formatStatisticsData)
                _model.value = UiModel.DashboardShow
            }

            _model.value = UiModel.LoadingHide
        }
    }

    fun onViewDetails() {
        _model.value = UiModel.NavigationToDetail(false)
    }

    fun onViewAllDetails() {
        _model.value = UiModel.NavigationToDetail(true)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}