package com.franpulido.emsproject.ui.main

import com.franpulido.data.repository.EmsRepository
import com.franpulido.usecases.GetHistoricalData
import com.franpulido.usecases.GetLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    @ViewModelScoped
    fun getHistoricalData(emsRepository: EmsRepository) = GetHistoricalData(emsRepository)

    @Provides
    @ViewModelScoped
    fun getLiveData(emsRepository: EmsRepository) = GetLiveData(emsRepository)
}