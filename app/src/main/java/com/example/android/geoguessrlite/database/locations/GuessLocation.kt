package com.example.android.geoguessrlite.database.locations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.geoguessrlite.database.GameCategory
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "guess_location_table")
data class GuessLocation(
    @PrimaryKey(autoGenerate = true)
    val locationId: Long = 0L,

    @ColumnInfo(name = "lat_lang")
    val locationLatLng: LatLng,

    @ColumnInfo(name = "location_category")
    var gameCategory: GameCategory,
)
