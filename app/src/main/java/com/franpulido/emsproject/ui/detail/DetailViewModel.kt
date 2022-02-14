package com.franpulido.emsproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.emsproject.ui.common.ScopedViewModel
import com.franpulido.usecases.GetHistoricalData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    @Named("allInfo") private val allInfo: Boolean,
    private val getHistoricalData: GetHistoricalData
) :
    ScopedViewModel() {

    sealed class UiModel {
        class ShowTotalValues(val totalValues: Int) : DetailViewModel.UiModel()
        class PaintGraph(val dataSets: ArrayList<ILineDataSet>) : DetailViewModel.UiModel()
        object ShowContent : DetailViewModel.UiModel()
        object LoadingShow : DetailViewModel.UiModel()
        object LoadingHide : DetailViewModel.UiModel()
        object Error : DetailViewModel.UiModel()
    }

    private val listElements = listOf("Building", "Grid", "Pv", "Quasar")
    private val colors = intArrayOf(
        ColorTemplate.VORDIPLOM_COLORS[0],
        ColorTemplate.VORDIPLOM_COLORS[1],
        ColorTemplate.VORDIPLOM_COLORS[2],
        ColorTemplate.VORDIPLOM_COLORS[3]
    )

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) paintXY()
            return _model
        }

    private fun paintXY() = launch {
        _model.value = UiModel.LoadingShow
        val historicalData =
            if (allInfo) getHistoricalData.invoke() else getHistoricalData.invoke().take(20)

        if (historicalData.isEmpty()) {
            _model.value = UiModel.Error
        } else {
            _model.value = UiModel.ShowContent
            _model.value = UiModel.ShowTotalValues(historicalData.size)
            _model.value = UiModel.PaintGraph(getDataSets(historicalData))
        }
        _model.value = UiModel.LoadingHide
    }

    private fun getDataSets(historicalData: List<HistoricalEmsData>): ArrayList<ILineDataSet> {
        val dataSets = ArrayList<ILineDataSet>()
        for ((index, elements) in listElements.withIndex()) {
            val values = ArrayList<Entry>()

            historicalData.mapIndexed { indexHistoricalData, data ->
                val arrayElements = arrayListOf(
                    data.buildingActivePower.toFloat(),
                    data.gridActivePower.toFloat(),
                    data.pvActivePower.toFloat(),
                    data.quasarsActivePower.toFloat()
                )

                //I have a problem with this library that only allows Float so the precision is lower.
                //When I try to cast Long to Float, and some points appear in the same position
                //val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(data.timeStamp).time
                //values.add(Entry(date.toFloat(), arrayElements[index]))

                values.add(Entry(indexHistoricalData.toFloat(), arrayElements[index]))
            }

            val d = LineDataSet(values, elements)
            d.lineWidth = 2.5f
            d.circleRadius = 4f
            val color = colors[index]
            d.color = color
            d.setCircleColor(color)
            dataSets.add(d)
        }
        return dataSets
    }

}