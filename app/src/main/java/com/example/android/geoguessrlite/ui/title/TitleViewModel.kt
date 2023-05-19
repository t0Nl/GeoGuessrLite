package com.example.android.geoguessrlite.ui.title

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.score.GameDuration

private val DEFAULT_GAME_TYPE = GameCategory.WORLD
private val DEFAULT_GAME_DURATION = GameDuration.ONE_MINUTE

class TitleViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _selectedGameType = MutableLiveData<String>()
    val selectedGameType: LiveData<String> get() = _selectedGameType

    private val _selectedGameDuration = MutableLiveData<String>()
    val selectedGameDuration: LiveData<String> get() = _selectedGameDuration

    init {
        _selectedGameType.value = DEFAULT_GAME_TYPE.label
        _selectedGameDuration.value = DEFAULT_GAME_DURATION.durationSeconds.toString()
    }

    fun setGameType(selectedGameType: String) {
        _selectedGameType.value = selectedGameType
    }

    fun setGameDuration(selectedGameDuration: String) {
        _selectedGameDuration.value = selectedGameDuration
    }
}