package com.franpulido.emsproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franpulido.emsproject.models.HistoricalEmsModel
import com.franpulido.emsproject.models.HistoricalEmsModelList
import com.franpulido.emsproject.ui.common.ScopedViewModel
import com.franpulido.emsproject.ui.common.colorsTheme
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.math.abs

@HiltViewModel
class DetailViewModel @Inject constructor(
    @Named("allData") private val allData: HistoricalEmsModelList
) :
    ScopedViewModel() {

    sealed class UiModel {
        class PaintGraph(val dataSets: ArrayList<ILineDataSet>, val totalValues: String) :
            DetailViewModel.UiModel()
    }

    private val listElements = listOf("Building", "Grid", "Pv", "Quasar")

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) paintXY()
            return _model
        }

    private fun paintXY() = launch {
        val historicalData: List<HistoricalEmsModel> = allData.results
        _model.value =
            UiModel.PaintGraph(getDataSets(historicalData), historicalData.size.toString())
    }

    private fun getDataSets(historicalData: List<HistoricalEmsModel>): ArrayList<ILineDataSet> {
        val dataSets = ArrayList<ILineDataSet>()
        for ((index, elements) in listElements.withIndex()) {
            val values = ArrayList<Entry>()

            historicalData.mapIndexed { indexHistoricalData, data ->
                val arrayElements = arrayListOf(
                    data.buildingActivePower.toFloat(),
                    data.gridActivePower.toFloat(),
                    data.pvActivePower.toFloat(),
                    abs(data.quasarsActivePower.toFloat())
                )

                //I have a problem with this library that only allows Float so the precision is lower.
                //When I try to cast Long to Float in some points appear the same position

                //val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(data.timeStamp).time
                //values.add(Entry(date.toFloat(), arrayElements[index]))

                values.add(Entry(indexHistoricalData.toFloat(), arrayElements[index]))
            }

            val d = LineDataSet(values, elements)
            d.lineWidth = 2.5f
            d.circleRadius = 4f
            val color = colorsTheme[index]
            d.color = color
            d.setCircleColor(color)
            dataSets.add(d)
        }
        return dataSets
    }

}