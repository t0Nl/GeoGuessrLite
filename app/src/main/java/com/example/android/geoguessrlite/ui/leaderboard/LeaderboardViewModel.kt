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
import com.example.android.geoguessrlite.ui.title.DEFAULT_GAME_DURATION
import com.example.android.geoguessrlite.ui.title.DEFAULT_GAME_TYPE
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val scoreDatabase = ScoreDatabase.getInstance(application).scoreDatabaseDao

    private val _gameScores = MutableLiveData<List<GameScore>>()
    val gameScores: LiveData<List<GameScore>> get() = _gameScores

    private val _selectedGameType = MutableLiveData<String>()
    val selectedGameType: LiveData<String> get() = _selectedGameType

    private val _selectedGameDuration = MutableLiveData<String>()
    val selectedGameDuration: LiveData<String> get() = _selectedGameDuration

    init {
        _selectedGameType.value = DEFAULT_GAME_TYPE.label
        _selectedGameDuration.value = DEFAULT_GAME_DURATION.label
    }

    fun setFilterParams(
        selectedGameDuration: String?,
        selectedGameType: String?
    ) {
        _selectedGameType.value = selectedGameType ?: DEFAULT_GAME_TYPE.label
        _selectedGameDuration.value = selectedGameDuration ?: DEFAULT_GAME_DURATION.label

        viewModelScope.launch {
            _gameScores.value = scoreDatabase.getFilteredScores(
                GameDuration.values().first { it.label == _selectedGameDuration.value },
                GameCategory.values().first { it.label == _selectedGameType.value },
            )
        }
    }
}
