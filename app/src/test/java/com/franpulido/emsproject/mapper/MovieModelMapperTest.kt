package com.franpulido.emsproject.mapper


import com.franpulido.emsproject.data.toDomainHistoricalData
import com.franpulido.emsproject.data.toDomainLiveData
import com.franpulido.emsproject.mocks.HistoricDataProvider
import com.franpulido.emsproject.mocks.LiveDataProvider
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieModelMapperTest {

    @Test
    fun testHistoricalEntityToHistoricalEms() {
        val historicalEntity = HistoricDataProvider.getDummyHistoricalEntity()
        val historicalEmsData = historicalEntity.toDomainHistoricalData()

        assertEquals(historicalEntity.building_active_power.toFloat(), historicalEmsData.buildingActivePower.toFloat())
        assertEquals(historicalEntity.grid_active_power.toFloat(), historicalEmsData.gridActivePower.toFloat())
        assertEquals(historicalEntity.quasars_active_power.toFloat(), historicalEmsData.quasarsActivePower.toFloat())
        assertEquals(historicalEntity.pv_active_power.toFloat(), historicalEmsData.pvActivePower.toFloat())
        assertEquals(historicalEntity.timestamp, historicalEmsData.timeStamp)
    }

    @Test
    fun testLiveDataEntityToLiveData() {
        val liveDataEntity = LiveDataProvider.getDummyLiveDataEntity()
        val liveData = liveDataEntity.toDomainLiveData()

        assertEquals(liveDataEntity.solar_power.toFloat(), liveData.solarPower.toFloat())
        assertEquals(liveDataEntity.quasars_power.toFloat(), liveData.quasarsPower.toFloat())
        assertEquals(liveDataEntity.grid_power.toFloat(), liveData.gridPower.toFloat())
        assertEquals(liveDataEntity.building_demand.toFloat(), liveData.buildingDemand.toFloat())
        assertEquals(liveDataEntity.system_soc.toFloat(), liveData.systemSoc.toFloat())
        assertEquals(liveDataEntity.total_energy.toFloat(), liveData.totalEnergy.toFloat())
        assertEquals(liveDataEntity.current_energy.toFloat(), liveData.currentEnergy.toFloat())
    }

}