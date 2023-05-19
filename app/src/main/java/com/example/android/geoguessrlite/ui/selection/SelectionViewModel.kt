package com.example.android.geoguessrlite.ui.selection

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration

class SelectionViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _fragmentSelectionType = MutableLiveData<SelectionType>()
    val fragmentSelectionType: LiveData<SelectionType> get() = _fragmentSelectionType

    private val _fragmentSelectionOptions = MutableLiveData<List<String>>()
    val fragmentSelectionOptions: LiveData<List<String>> get() = _fragmentSelectionOptions

    fun setSelectionOptions(selectionType: String) {
        _fragmentSelectionType.value = selectionType.toSelectionType()
        _fragmentSelectionOptions.value = fragmentSelectionType.value?.toSelectionOptions()
        Log.e("TONI", "Something")
    }
}

private fun SelectionType.toSelectionOptions(): List<String> {
    return when (this) {
        SelectionType.GAME_TYPE -> GameCategory.values().map { it.label }
        SelectionType.GAME_DURATION -> GameDuration.values().map { it.label }
    }
}

private fun String.toSelectionType(): SelectionType {
    return SelectionType.values().first { it.label == this }
}
