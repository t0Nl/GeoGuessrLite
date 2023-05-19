package com.example.android.geoguessrlite.database.score

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration

@Entity(tableName = "game_score_table")
data class GameScore(
    @PrimaryKey(autoGenerate = true)
    val scoreId: Long = 0L,

    @ColumnInfo(name = "final_score")
    val finalScore: Int,

    @ColumnInfo(name = "game_duration")
    val gameDuration: GameDuration,

    @ColumnInfo(name = "game_category")
    var gameCategory: GameCategory,
)

