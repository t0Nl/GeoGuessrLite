package com.example.android.geoguessrlite.database.locations

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.score.GameDuration
import com.example.android.geoguessrlite.database.score.GameScore

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
    @Query("SELECT * FROM guess_location_table WHERE scoreId = :key")
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
    @Query("SELECT * FROM guess_location_table WHERE gameDuration=:gameDuration AND gameCategory=:gameCategory ORDER BY finalScore DESC")
    fun getRandomLocation(gameCategory: GameCategory): LiveData<List<GameScore>>

    @Query("SELECT * FROM guess_location_table WHERE gameDuration=:gameDuration AND gameCategory=:gameCategory ORDER BY finalScore DESC")
    fun getRandomLocationFromCategory(gameDuration: GameDuration, gameCategory: GameCategory): LiveData<List<GameScore>>
}
