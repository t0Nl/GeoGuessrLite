package com.example.android.geoguessrlite.ui.game

import com.example.android.geoguessrlite.database.GameCategory
import com.google.android.gms.maps.model.LatLng

class MapCameraResetValues {
    companion object {
        val resetLatLngForGameType = mapOf(
            GameCategory.WORLD.label to LatLng(23.50, -22.25),
            GameCategory.EUROPE.label to LatLng(48.70, 14.75),
            GameCategory.AFRICA.label to LatLng(5.0, 17.0),
            GameCategory.ASIA.label to LatLng(37.78, 100.13),
            GameCategory.AUSTRALIA_PACIFIC.label to LatLng(-27.79, 145.89),
            GameCategory.SOUTH_AMERICA.label to LatLng(-19.07, -59.54),
            GameCategory.NORTH_AMERICA.label to LatLng(40.70, -100.34),
        )

        val resetZoomLevelForGameType = mapOf(
            GameCategory.WORLD.label to 1f,
            GameCategory.EUROPE.label to 3f,
            GameCategory.AFRICA.label to 3f,
            GameCategory.ASIA.label to 1f,
            GameCategory.AUSTRALIA_PACIFIC.label to 3f,
            GameCategory.SOUTH_AMERICA.label to 3f,
            GameCategory.NORTH_AMERICA.label to 3f,
        )
    }
}
