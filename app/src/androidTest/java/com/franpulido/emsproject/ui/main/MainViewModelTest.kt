package com.franpulido.emsproject.ui.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.franpulido.data.repository.EmsRepository
import com.franpulido.emsproject.data.server.EmsMockDataSource
import com.franpulido.emsproject.ui.getOrAwaitValue
import com.franpulido.usecases.GetHistoricalData
import com.franpulido.usecases.GetLiveData
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainViewModelTest : TestCase() {

    private lateinit var viewModel: MainViewModel

    @Before
    public override fun setUp() {
        super.setUp()

        val remoteDataSource = EmsMockDataSource()
        val emsRepository = EmsRepository(remoteDataSource)

        val getLiveData = GetLiveData(emsRepository)
        val getHistoricalData = GetHistoricalData(emsRepository)
        viewModel = MainViewModel(getHistoricalData, getLiveData)
    }

    @Test
    fun testViewModelNotNull() {
        viewModel.initUi()
        val result: MainViewModel.UiModel = viewModel.model.getOrAwaitValue()
        assertThat(result != null).isTrue()
    }

}