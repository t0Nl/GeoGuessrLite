package com.example.android.geoguessrlite.util

import com.example.android.geoguessrlite.database.GameCategory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class ContinentFinder {
    
    private val northAmericaBounds = getContinentBounds(GameCategory.NORTH_AMERICA)

    private val southAmericaBounds = getContinentBounds(GameCategory.SOUTH_AMERICA)

    private val europeBounds = getContinentBounds(GameCategory.EUROPE)

    private val africaBounds = getContinentBounds(GameCategory.AFRICA)

    private val asiaBounds = getContinentBounds(GameCategory.ASIA)

    private val australiaPacificBounds = getContinentBounds(GameCategory.AUSTRALIA_PACIFIC)

    private fun getContinentBounds(continent: GameCategory): LatLngBounds {
        val boundsBuilder = LatLngBounds.Builder()

        val latitudes = when (continent) {
            GameCategory.NORTH_AMERICA -> ContinentsLatLng.LatNorthAmerica
            GameCategory.SOUTH_AMERICA -> ContinentsLatLng.LatSouthAmerica
            GameCategory.EUROPE -> ContinentsLatLng.LatEurope
            GameCategory.AFRICA -> ContinentsLatLng.LatAfrica
            GameCategory.ASIA -> ContinentsLatLng.LatAsia
            else -> ContinentsLatLng.LatAustraliaPacific
        }

        val longitudes = when (continent) {
            GameCategory.NORTH_AMERICA -> ContinentsLatLng.LngNorthAmerica
            GameCategory.SOUTH_AMERICA -> ContinentsLatLng.LngSouthAmerica
            GameCategory.EUROPE -> ContinentsLatLng.LngEurope
            GameCategory.AFRICA -> ContinentsLatLng.LngAfrica
            GameCategory.ASIA -> ContinentsLatLng.LngAsia
            else -> ContinentsLatLng.LngAustraliaPacific
        }

        latitudes.forEachIndexed { index, _ ->
            boundsBuilder.include(
                LatLng(
                    latitudes[index],
                    longitudes[index]
                )
            )
        }
        return boundsBuilder.build()
    }

    fun getContinent(location: LatLng): GameCategory? {
        if (northAmericaBounds.contains(location)) {
            return GameCategory.NORTH_AMERICA
        }
        if (southAmericaBounds.contains(location)) {
            return GameCategory.SOUTH_AMERICA
        }
        if (europeBounds.contains(location)) {
            return GameCategory.EUROPE
        }
        if (africaBounds.contains(location)) {
            return GameCategory.AFRICA
        }
        if (asiaBounds.contains(location)) {
            return GameCategory.ASIA
        }
        if (australiaPacificBounds.contains(location)) {
            return GameCategory.AUSTRALIA_PACIFIC
        }
        return null
    }
}