package com.franpulido.emsproject.ui.detail


import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.franpulido.emsproject.R
import com.franpulido.emsproject.databinding.ActivityDetailBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val ALL_INFO = "DetailActivity:info"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.xy_graph)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.model.observe(this, Observer(::updateUi))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUi(model: DetailViewModel.UiModel) {
        when (model) {
            DetailViewModel.UiModel.Error -> binding.layoutError.viewError.visibility = View.VISIBLE
            is DetailViewModel.UiModel.PaintGraph -> {
                with(binding.chartView) {
                    setDrawGridBackground(false)
                    description.isEnabled = true
                    description.text = getString(R.string.xy_graph)
                    setDrawBorders(false)

                    axisLeft.isEnabled = false
                    axisRight.setDrawAxisLine(true)
                    axisRight.setDrawGridLines(true)
                    xAxis.setDrawAxisLine(true)
                    xAxis.setDrawGridLines(true)

                    axisRight.valueFormatter = MyYAxisFormatter()
                    axisRight.granularity = 1f

                    //With this value formatter the axis X would show 26-09 22:03h
                    /*xAxis.valueFormatter = MyXAxisFormatter()
                    xAxis.granularity = 1f*/

                    setTouchEnabled(true)
                    isDragEnabled = true
                    setScaleEnabled(true)
                    setPinchZoom(true)

                    with(legend){
                        verticalAlignment = Legend.LegendVerticalAlignment.TOP
                        horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                        orientation = Legend.LegendOrientation.HORIZONTAL
                        textSize = 12f
                        formToTextSpace = 8f
                        xEntrySpace = 16f
                        yEntrySpace = 16f
                        setDrawInside(false)
                    }

                    extraTopOffset
                    val data = LineData(model.dataSets)
                    setData(data)
                    invalidate()
                }
            }
            is DetailViewModel.UiModel.ShowTotalValues -> binding.tvTiTleDetail.text =
                getString(R.string.total_of_elements_showed, model.totalValues.toString())
            DetailViewModel.UiModel.LoadingHide -> binding.progress.visibility = View.GONE
            DetailViewModel.UiModel.LoadingShow -> binding.progress.visibility = View.VISIBLE
            DetailViewModel.UiModel.ShowContent -> binding.llDetailContent.visibility = View.VISIBLE
        }

    }

    class MyYAxisFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            return "$value kWh"
        }
    }

    class MyXAxisFormatter : IAxisValueFormatter {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            val valueToLong = value.toLong()
            val date = SimpleDateFormat("dd-MM HH:mm").format(valueToLong)
            return "$date h"
        }
    }

}