package com.example.android.geoguessrlite.database.locations

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.geoguessrlite.database.GameCategory

@Dao
interface GuessLocationDao {

    @Insert
    suspend fun insert(guessLocation: GuessLocation)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    suspend fun update(guessLocation: GuessLocation)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * FROM guess_location_table WHERE locationId = :key")
    suspend fun get(key: Long): GuessLocation?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM guess_location_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM guess_location_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomLocation(): GuessLocation

    @Query("SELECT * FROM guess_location_table WHERE location_category = :locationCategory ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomLocationFromRegion(locationCategory: GameCategory): GuessLocation
}
