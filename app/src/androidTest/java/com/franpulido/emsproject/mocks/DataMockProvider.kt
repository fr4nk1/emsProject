package com.franpulido.emsproject.mocks

import com.franpulido.domain.models.HistoricalEmsData
import com.franpulido.domain.models.LiveEmsData
import com.franpulido.emsproject.models.HistoricalEmsModel
import com.franpulido.emsproject.models.HistoricalEmsModelList

class DataMockProvider {

    companion object {

        private const val SOLAR_POWER = 7.827
        private const val QUASARS_POWER = -38.732
        private const val GRID_POWER = 80.475
        private const val BUILDING_DEMAND = 127.03399999999999
        private const val SYSTEM_SOC = 48.333333333333336
        private const val TOTAL_ENERGY = 960.0
        private const val CURRENT_ENERGY = 464.0
        private const val BUILDING_POWER = 142.31273333333328
        private const val PV_POWER = 22.475533333333335
        private const val TIMESTAMP = "2021-09-27T16:06:00+00:00"

        fun getDummyHistorical() = HistoricalEmsData(
            BUILDING_POWER, GRID_POWER, PV_POWER, QUASARS_POWER, TIMESTAMP
        )

        fun getDummyHistoricalList() = HistoricalEmsModelList(
            listOf(
                HistoricalEmsModel(
                    BUILDING_POWER,
                    GRID_POWER,
                    PV_POWER,
                    QUASARS_POWER,
                    TIMESTAMP
                )
            )
        )

        fun getDummyLiveData() = LiveEmsData(
            SOLAR_POWER,
            QUASARS_POWER,
            GRID_POWER,
            BUILDING_DEMAND,
            SYSTEM_SOC,
            TOTAL_ENERGY,
            CURRENT_ENERGY
        )

    }
}