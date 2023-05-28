package com.example.android.geoguessrlite.ui.game

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.locations.GuessLocation
import com.example.android.geoguessrlite.database.locations.LocationDatabase
import com.example.android.geoguessrlite.network.GuessLocationsApi
import com.example.android.geoguessrlite.util.ContinentFinder
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
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

    val locationDatabase = LocationDatabase.getInstance(application).guessLocationDao

    private var gameType = GameCategory.WORLD

    private val usedLocations = mutableListOf<LatLng>()

    private lateinit var timer: CountDownTimer

    private val _guessCompleted = MutableLiveData<Boolean>()
    val guessCompleted: LiveData<Boolean> = _guessCompleted

    private val _streetViewLoaded = MutableLiveData<Boolean>()
    val streetViewLoaded: LiveData<Boolean> = _streetViewLoaded

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

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

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
        loadNextLocation()
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

    fun loadNextLocation() {
        _streetViewLoaded.value = false

        viewModelScope1.launch {
            try {
                val continentFinder = ContinentFinder()
                val locations = GuessLocationsApi.retrofitService.getProperties()
                var locationContinent: GameCategory? = null

                for (location in locations) {
                    val guessLocationBounds = LatLngBounds.Builder()
                        .include(
                            LatLng(
                                location.bounds.max.lat,
                                location.bounds.max.lng
                            )
                        )
                        .include(
                            LatLng(
                                location.bounds.min.lat,
                                location.bounds.min.lng
                            )
                        )
                        .build()

                    locationContinent = continentFinder.getContinent(guessLocationBounds.center)

                    if (gameType == GameCategory.WORLD) {
                        if (locationContinent != null && !usedLocations.contains(guessLocationBounds.center)) {
                            _streetViewLocation.value = guessLocationBounds.center

                            usedLocations.add(guessLocationBounds.center)

                            try {
                                locationDatabase.insert(
                                    GuessLocation(
                                        locationLatitude = guessLocationBounds.center.latitude,
                                        locationLongitude = guessLocationBounds.center.longitude,
                                        gameCategory = locationContinent,
                                    )
                                )
                            } catch (e: Exception) {
                                Log.e("TONI", "${e.message}")
                            }


                            break
                        }
                    } else {
                        if (locationContinent != null && !usedLocations.contains(guessLocationBounds.center) && locationContinent == gameType) {
                            _streetViewLocation.value = guessLocationBounds.center

                            usedLocations.add(guessLocationBounds.center)

                            locationDatabase.insert(
                                GuessLocation(
                                    locationLatitude = guessLocationBounds.center.latitude,
                                    locationLongitude = guessLocationBounds.center.longitude,
                                    gameCategory = locationContinent,
                                )
                            )

                            break
                        }
                    }
                }
                if (locationContinent == null) {
                    getLocationFromDatabase()
                }
            } catch (e: Exception) {
                Log.e("TONI", "${e.message}")
                _eventGameFinish.value = true
            }
        }
    }

    private fun getLocationFromDatabase() {
        if (gameType == GameCategory.WORLD) {

        } else {

        }
    }

    fun startNextLocation() {
        _guessCompleted.value = false
    }

    fun streetViewLoaded() {
        _streetViewLoaded.value = true
    }

    fun setGameType(selectedGameType: GameCategory) {
        gameType = selectedGameType
    }

    fun startTimer() {
        timer =
            object : CountDownTimer(_currentTime.value?.times(1000) ?: COUNTDOWN_TIME, ONE_SECOND) {
                override fun onTick(millisUntilFinished: Long) {
                    _currentTime.value = (millisUntilFinished / ONE_SECOND)
                }

                override fun onFinish() {
                    _eventGameFinish.value = true
                }
            }

        timer.start()
    }
}