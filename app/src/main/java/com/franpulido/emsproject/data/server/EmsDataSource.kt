package com.franpulido.emsproject.data.server

import com.franpulido.data.source.RemoteDataSource
import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData
import com.franpulido.emsproject.data.toDomainHistoricalData
import com.franpulido.emsproject.data.toDomainLiveData

class EmsDataSource : RemoteDataSource {

    override suspend fun getHistoricalData(): List<HistoricalEmsData> =
        EmsClient.service
            .listHistoricalData()
            .map { it.toDomainHistoricalData() }

    override suspend fun getLiveData(): LiveEmsData = EmsClient.service
        .getLiveData().toDomainLiveData()
}