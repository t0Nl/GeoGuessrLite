package com.example.android.geoguessrlite.database.locations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.android.geoguessrlite.database.GameCategory
import com.google.android.gms.maps.model.LatLng

@Entity(
    tableName = "guess_location_table",
    indices = [
        Index(
            value = ["latitude", "longitude"],
            unique = true
        )
    ]
)
data class GuessLocation(
    @PrimaryKey(autoGenerate = true)
    val locationId: Long = 0L,

    @ColumnInfo(name = "latitude")
    val locationLatitude: Double,

    @ColumnInfo(name = "longitude")
    val locationLongitude: Double,

    @ColumnInfo(name = "location_category")
    var gameCategory: GameCategory,
)
