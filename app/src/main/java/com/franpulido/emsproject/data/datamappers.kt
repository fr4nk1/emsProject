package com.franpulido.emsproject.data

import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData
import com.franpulido.emsproject.data.database.HistoricalEmsDataEntity
import com.franpulido.emsproject.data.database.LiveEmsDataEntity

fun HistoricalEmsDataEntity.toDomainHistoricalData(): HistoricalEmsData =
    HistoricalEmsData(building_active_power, grid_active_power, pv_active_power, quasars_active_power, timestamp)

fun LiveEmsDataEntity.toDomainLiveData(): LiveEmsData =
    LiveEmsData(solar_power, quasars_power, grid_power, building_demand, system_soc, total_energy, current_energy)
