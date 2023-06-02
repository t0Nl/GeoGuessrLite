package com.example.android.geoguessrlite.ui.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration
import com.example.android.geoguessrlite.database.locations.LocationDatabase
import com.example.android.geoguessrlite.database.score.GameScore
import com.example.android.geoguessrlite.database.score.ScoreDatabase
import kotlinx.coroutines.launch

class ResultViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val scoreDatabase = ScoreDatabase.getInstance(application).scoreDatabaseDao

    fun saveScoreToDataBase(
        finalScore: Long,
        gameDuration: Int,
        gameType: String,
    ) {
        if (finalScore > 0) {
            viewModelScope.launch {
                scoreDatabase.insert(
                    GameScore(
                        finalScore = finalScore,
                        gameDuration = GameDuration.values().first { it.durationSeconds == gameDuration },
                        gameCategory = GameCategory.values().first { it.label == gameType },
                    )
                )
            }
        }
    }
}
