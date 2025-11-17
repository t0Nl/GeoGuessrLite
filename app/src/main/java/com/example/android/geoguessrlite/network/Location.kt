package com.example.android.geoguessrlite.network

data class Location(
    val bounds: Bounds
)

data class Bounds(
    val min: Limits,
    val max: Limits,
)

data class Limits(
    val lat: Double,
    val lng: Double,
)
