package com.example.android.geoguessrlite.database.score

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.geoguessrlite.database.GameCategory

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

enum class GameDuration(val durationSeconds: Int) {
        ONE_MINUTE(60),
        THREE_MINUTES(180),
        FIVE_MINUTES(300),
}
