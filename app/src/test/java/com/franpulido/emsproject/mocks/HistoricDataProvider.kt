package com.franpulido.emsproject.mocks

import com.franpulido.emsproject.data.entities.HistoricalEmsDataEntity

class HistoricDataProvider {

    companion object {

        private const val BUILDING_POWER = 142.31273333333328
        private const val GRID_POWER = 89.78836666666669
        private const val PV_POWER = 22.475533333333335
        private const val QUASARS_POWER = -30.048833333333324
        private const val TIMESTAMP = "2021-09-27T16:06:00+00:00"

        fun getDummyHistoricalEntity() = HistoricalEmsDataEntity(
            BUILDING_POWER, GRID_POWER, PV_POWER, QUASARS_POWER, TIMESTAMP
        )

    }

}
