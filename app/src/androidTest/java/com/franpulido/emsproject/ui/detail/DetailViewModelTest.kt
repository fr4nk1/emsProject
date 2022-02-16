package com.franpulido.emsproject.ui.detail

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.franpulido.emsproject.mocks.DataMockProvider.Companion.getDummyHistoricalList
import com.franpulido.emsproject.ui.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailViewModelTest : TestCase() {

    private lateinit var viewModel: DetailViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        viewModel = DetailViewModel(getDummyHistoricalList())
    }

    @Test
    fun testViewModelNotNull() {
        val result: DetailViewModel.UiModel = viewModel.model.getOrAwaitValue()
        assertThat(result != null).isTrue()
    }

}