package com.franpulido.usecases

import com.franpulido.data.repository.EmsRepository
import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData


class GetHistoricalData(private val emsRepository: EmsRepository) {
    suspend fun invoke(): List<HistoricalEmsData> = try {
        emsRepository.getHistoricalData()
    } catch (e: Exception) {
        emptyList()
    }
}

class GetLiveData(private val emsRepository: EmsRepository) {
    suspend fun invoke(): LiveEmsData = try {
        emsRepository.getLiveData()
    } catch (e: Exception) {
        LiveEmsData(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    }
}
