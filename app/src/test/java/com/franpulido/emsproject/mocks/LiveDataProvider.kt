package com.franpulido.emsproject.mocks

import com.franpulido.emsproject.data.entities.LiveEmsDataEntity

class LiveDataProvider {

    companion object {

        private const val SOLAR_POWER = 7.827
        private const val QUASARS_POWER = -38.732
        private const val GRID_POWER = 80.475
        private const val BUILDING_DEMAND = 127.03399999999999
        private const val SYSTEM_SOC = 48.333333333333336
        private const val TOTAL_ENERGY = 960.0
        private const val CURRENT_ENERGY = 464.0


        fun getDummyLiveDataEntity() = LiveEmsDataEntity(
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
