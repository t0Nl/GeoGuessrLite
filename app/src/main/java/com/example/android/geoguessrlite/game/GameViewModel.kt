package com.example.android.geoguessrlite.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.*
import com.example.android.geoguessrlite.network.GuessLocationsApi
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope as viewModelScope1

class GameViewModel(
    application: Application
) : AndroidViewModel(application) {
    companion object {
        private const val DONE = 0L

        private const val COUNTDOWN_PANIC_SECONDS = 10L

        private const val ONE_SECOND = 1000L

        private const val COUNTDOWN_TIME = 60L
    }

    private lateinit var timer: CountDownTimer

    private val _guessCompleted = MutableLiveData<Boolean>()
    val guessCompleted: LiveData<Boolean> = _guessCompleted

    private val _streetViewLocation = MutableLiveData<LatLng>()
    val streetViewLocation: LiveData<LatLng> = _streetViewLocation

    private val _targetMarker = MutableLiveData<Marker?>()
    val targetMarker: LiveData<Marker?> = _targetMarker

    private val _currentMarker = MutableLiveData<Marker?>()
    val currentMarker: LiveData<Marker?> = _currentMarker

    private val _resultPolyline = MutableLiveData<Polyline?>()
    val resultPolyline: LiveData<Polyline?> = _resultPolyline

    private val _guessPoints = MutableLiveData<Int>()
    val guessPoints: LiveData<Int> = _guessPoints

    private val _gameScore = MutableLiveData<Int>()
    val gameScore: LiveData<Int> = _gameScore

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        _guessCompleted.value = false
        _currentTime.value = COUNTDOWN_TIME
        _guessPoints.value = 0
        _gameScore.value = 0
        startTimer()
        getGuessLocation()
    }

    fun updateCurrentMarker(newMarker: Marker?) {
        _currentMarker.value = newMarker
    }

    fun updateTargetMarker(targetMarker: Marker?) {
        _targetMarker.value = targetMarker
    }

    fun updateResultPolyline(resultPolyline: Polyline?) {
        _resultPolyline.value = resultPolyline
    }

    fun locationGuessed(distanceFromTarget: Float) {
        _guessCompleted.value = true
        timer.cancel()
        val guessScore = calculateGuessScore(distanceFromTarget)
        _guessPoints.value = guessScore
        _gameScore.value = _gameScore.value?.plus(guessScore)
    }

    private fun calculateGuessScore(distanceFromTarget: Float): Int {
        return ((21000000L - distanceFromTarget) / 100000).toInt()
    }

    private fun getGuessLocation() {
        viewModelScope1.launch {
            try {
                val bounds = GuessLocationsApi.retrofitService.getProperties()
                _streetViewLocation.value = LatLng(
                    (bounds.first().bounds.max.lat + bounds.first().bounds.min.lat)/2,
                    (bounds.first().bounds.max.lng + bounds.first().bounds.min.lng)/2
                )
            } catch (e: Exception) {
                gameEnd()
            }
        }
    }

    fun loadNextLocation() {
        getGuessLocation()
        _guessCompleted.value = false
    }

    fun startTimer() {
        timer = object : CountDownTimer(_currentTime.value?.times(1000) ?: COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                gameEnd()
            }
        }

        timer.start()
    }

    fun gameEnd() {

    }
}