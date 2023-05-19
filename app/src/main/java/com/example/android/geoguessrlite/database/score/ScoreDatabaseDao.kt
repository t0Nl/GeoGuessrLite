package com.example.android.geoguessrlite.database.score

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration

@Dao
interface ScoreDatabaseDao {

    @Insert
    suspend fun insert(score: GameScore)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    suspend fun update(score: GameScore)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * FROM game_score_table WHERE scoreId = :key")
    suspend fun get(key: Long): GameScore?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM game_score_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM game_score_table WHERE game_duration = :gameDuration AND game_category = :gameCategory ORDER BY final_score DESC")
    fun getFilteredScores(gameDuration: GameDuration, gameCategory: GameCategory): LiveData<List<GameScore>>
}
