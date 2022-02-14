package com.franpulido.emsproject.ui.main

import android.annotation.SuppressLint
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
                putExtra(DetailActivity.ALL_INFO, model.retrieveAllInfo)
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
            binding.layoutStatitics.tvBuildingEnergy.text =  getString(R.string.statitics_of_each_source, buildingDemand.toString())
        }
    }
}
