package com.franpulido.emsproject.ui.main

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.franpulido.emsproject.R
import com.franpulido.emsproject.databinding.ActivityMainBinding
import com.franpulido.emsproject.ui.common.startActivity
import com.franpulido.emsproject.ui.detail.DetailActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel.model.observe(this, Observer(::updateUi))
        binding.layoutStatitics.tvViewDetails.setOnClickListener { viewModel.onViewDetails() }
        binding.layoutStatitics.tvAllViewDetails.setOnClickListener { viewModel.onViewAllDetails() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_info -> {
                InfoBottomSheetFragment.newInstance().show(supportFragmentManager, "")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateUi(model: MainViewModel.UiModel) {
        when (model) {
            is MainViewModel.UiModel.ContentLiveData -> paintInfoLiveData(model)
            is MainViewModel.UiModel.NavigationToDetail -> startActivity<DetailActivity> {
                putExtra(DetailActivity.ALL_DATA, model.historicalData)
            }
            is MainViewModel.UiModel.ContentStatistics -> paintStatisticsData(model)
            MainViewModel.UiModel.Init -> viewModel.initUi()
            MainViewModel.UiModel.Error -> binding.layoutError.viewError.visibility = View.VISIBLE
            MainViewModel.UiModel.LoadingShow -> binding.progress.visibility = View.VISIBLE
            MainViewModel.UiModel.LoadingHide -> binding.progress.visibility = View.GONE

            is MainViewModel.UiModel.ShowQuasarCharged -> binding.layoutQuasar.tvQuasarCharged.text =
                getString(R.string.value_in_kwh, model.totalQuasarCharged.toString())
            is MainViewModel.UiModel.ShowQuasarDischarged -> binding.layoutQuasar.tvQuasarDischarged.text =
                getString(R.string.value_in_kwh, model.totalQuasarDischarged.toString())
            MainViewModel.UiModel.DashboardHide -> binding.rlDashboard.visibility = View.GONE
            MainViewModel.UiModel.DashboardShow -> binding.rlDashboard.visibility = View.VISIBLE
        }
    }

    private fun paintInfoLiveData(model: MainViewModel.UiModel.ContentLiveData) {
        with(model.liveData) {
            binding.layoutLiveData.tvSolarPower.text = getString(R.string.value_in_kwh, solarPower.toString())
            binding.layoutLiveData.tvQuasarPower.text =  getString(R.string.value_in_kwh, quasarsPower.toString())
            binding.layoutLiveData.tvGridPower.text =  getString(R.string.value_in_kwh, gridPower.toString())
            binding.layoutLiveData.tvBuildingDemand.text =  getString(R.string.value_in_kwh, buildingDemand.toString())
            binding.layoutLiveData.tvTotalEnergy.text =  getString(R.string.value_in_kwh, totalEnergy.toString())
            binding.layoutLiveData.tvCurrentEnergy.text =  getString(R.string.value_in_kwh, currentEnergy.toString())
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun paintStatisticsData(model: MainViewModel.UiModel.ContentStatistics) {

        with(model.statistics) {
            binding.layoutStatitics.tvSolarPower.text = getString(R.string.value_in_percentage, solarPower.toString())
            binding.layoutStatitics.tvQuasarPower.text =  getString(R.string.value_in_percentage, quasarsPower.toString())
            binding.layoutStatitics.tvGridPower.text =  getString(R.string.value_in_percentage, gridPower.toString())
            binding.layoutStatitics.tvBuildingEnergy.text =  getString(R.string.statistics_of_each_source, buildingDemand.toString())
        }

        paintChart(model)
    }

    private fun paintChart(model: MainViewModel.UiModel.ContentStatistics) {
        binding.layoutStatitics.chart.setUsePercentValues(true)
        binding.layoutStatitics.chart.description.isEnabled = false
        binding.layoutStatitics.chart.setExtraOffsets(5f, 10f, 5f, 5f)

        binding.layoutStatitics.chart.dragDecelerationFrictionCoef = 0.95f

        binding.layoutStatitics.chart.isDrawHoleEnabled = true
        binding.layoutStatitics.chart.setHoleColor(Color.TRANSPARENT)

        binding.layoutStatitics.chart.setTransparentCircleColor(Color.TRANSPARENT)
        binding.layoutStatitics.chart.setTransparentCircleAlpha(110)

        binding.layoutStatitics.chart.holeRadius = 58f
        binding.layoutStatitics.chart.transparentCircleRadius = 61f

        binding.layoutStatitics.chart.rotationAngle = 0f
        binding.layoutStatitics.chart.isRotationEnabled = true
        binding.layoutStatitics.chart.isHighlightPerTapEnabled = true


        binding.layoutStatitics.chart.animateY(1400, Easing.EaseInOutQuad)

        binding.layoutStatitics.chart.setEntryLabelColor(Color.TRANSPARENT)

        val legend = binding.layoutStatitics.chart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.xEntrySpace = 7f
        legend.yEntrySpace = 0f
        legend.yOffset = 0f

        when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> legend.textColor = Color.WHITE
            Configuration.UI_MODE_NIGHT_NO -> legend.textColor = Color.BLACK
        }

        val data = PieData(model.dataSetStatistics)
        data.setValueTextSize(0f)
        data.setValueTextColor(Color.TRANSPARENT)
        binding.layoutStatitics.chart.data = data

        binding.layoutStatitics.chart.highlightValues(null)
        binding.layoutStatitics.chart.invalidate()
    }

}
