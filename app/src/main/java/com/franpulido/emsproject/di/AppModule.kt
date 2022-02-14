package com.franpulido.emsproject.di

import com.franpulido.data.source.RemoteDataSource
import com.franpulido.emsproject.data.server.EmsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = EmsDataSource()
}