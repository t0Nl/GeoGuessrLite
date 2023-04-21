package com.example.android.geoguessrlite.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline

class GameViewModel(
    application: Application
) : AndroidViewModel(application) {
    companion object {
        private const val DONE = 0L

        private const val COUNTDOWN_PANIC_SECONDS = 10L

        private const val ONE_SECOND = 1000L

        private const val COUNTDOWN_TIME = 60000L
    }

    private var timer: CountDownTimer

    private val _guessCompleted = MutableLiveData<Boolean>()
    val guessCompleted: LiveData<Boolean> = _guessCompleted

    private val _timerPaused = MutableLiveData<Boolean>()
    val timerPaused: LiveData<Boolean> = _timerPaused

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
        _timerPaused.value = false
        _guessPoints.value = 0
        _gameScore.value = 0
        _streetViewLocation.value = LatLng(45.333706614220254, 14.454505105161306)

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                if (_timerPaused.value == false) {
                    _currentTime.value = (millisUntilFinished / ONE_SECOND)
                }
            }

            override fun onFinish() {
                _currentTime.value = currentTime.value
                gameEnd()
            }
        }

        timer.start()
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
        _timerPaused.value = true
        val guessScore = calculateGuessScore(distanceFromTarget)
        _guessPoints.value = guessScore
        _gameScore.value = _gameScore.value?.plus(guessScore)
    }

    private fun calculateGuessScore(distanceFromTarget: Float): Int {
        return ((21000000L - distanceFromTarget) / 100000).toInt()
    }

    fun loadNextLocation() {
        _guessCompleted.value = false
        _timerPaused.value = false
    }

    fun gameEnd() {

    }
}