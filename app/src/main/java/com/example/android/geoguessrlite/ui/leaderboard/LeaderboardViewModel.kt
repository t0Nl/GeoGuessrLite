package com.example.android.geoguessrlite.ui.leaderboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration
import com.example.android.geoguessrlite.database.score.GameScore
import com.example.android.geoguessrlite.database.score.ScoreDatabase
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val scoreDatabase = ScoreDatabase.getInstance(application).scoreDatabaseDao

    private val _gameScores = MutableLiveData<List<GameScore>>()
    val gameScores: LiveData<List<GameScore>> get() = _gameScores

    init {
        viewModelScope.launch {
            _gameScores.value = scoreDatabase.getFilteredScores(GameDuration.ONE_MINUTE, GameCategory.WORLD)
        }
    }
}
