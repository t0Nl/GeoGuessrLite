package com.example.android.geoguessrlite.database.locations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.geoguessrlite.database.GameCategory

@Dao
interface LocationDatabase {

    @Insert
    suspend fun insert(location: GuessLocation)

    @Update
    suspend fun update(location: GuessLocation)

    @Query("SELECT * FROM guess_location_table WHERE locationId = :key")
    suspend fun get(key: Long): GuessLocation?

    @Query("DELETE FROM guess_location_table")
    suspend fun clear()

    @Query("SELECT * FROM guess_location_table ORDER BY RAND() LIMIT 1")
    fun getRandomLocation(): GuessLocation

    @Query("SELECT * FROM guess_location_table WHERE location_category = :locationCategory ORDER BY RAND() LIMIT 1")
    fun getRandomLocationFromRegion(locationCategory: GameCategory): GuessLocation
}
