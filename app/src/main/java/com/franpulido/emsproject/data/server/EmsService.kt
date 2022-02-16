package com.franpulido.emsproject.data.server

import com.franpulido.emsproject.data.entities.HistoricalEmsDataEntity
import com.franpulido.emsproject.data.entities.LiveEmsDataEntity
import retrofit2.http.GET

interface EmsService {
    @GET("carandahe/ems-demo-project/-/raw/main/historic_data.json")
    suspend fun listHistoricalData(): List<HistoricalEmsDataEntity>

    @GET("carandahe/ems-demo-project/-/raw/main/live_data.json")
    suspend fun getLiveData(): LiveEmsDataEntity
}

