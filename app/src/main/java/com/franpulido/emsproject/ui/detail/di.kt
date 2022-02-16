package com.franpulido.emsproject.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.franpulido.emsproject.models.HistoricalEmsModelList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DetailActivityModule {

    @Provides
    @Named("allData")
    fun allDataProvider(stateHandle: SavedStateHandle): HistoricalEmsModelList =
        stateHandle.get<HistoricalEmsModelList>(DetailActivity.ALL_DATA) ?: HistoricalEmsModelList(
            listOf())
}