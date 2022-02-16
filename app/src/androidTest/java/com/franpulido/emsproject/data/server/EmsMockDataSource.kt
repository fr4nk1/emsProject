package com.franpulido.emsproject.data.server

import com.franpulido.data.source.RemoteDataSource
import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData
import com.franpulido.emsproject.mocks.DataMockProvider.Companion.getDummyHistorical
import com.franpulido.emsproject.mocks.DataMockProvider.Companion.getDummyLiveData

class EmsMockDataSource : RemoteDataSource {

    override suspend fun getHistoricalData(): List<HistoricalEmsData> = listOf(getDummyHistorical())

    override suspend fun getLiveData(): LiveEmsData = getDummyLiveData()
}