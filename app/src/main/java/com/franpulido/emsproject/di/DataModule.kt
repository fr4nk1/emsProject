package com.franpulido.emsproject.di

import com.franpulido.data.repository.EmsRepository
import com.franpulido.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun emsRepositoryProvider(remoteDataSource: RemoteDataSource,
    ) = EmsRepository(remoteDataSource)
}