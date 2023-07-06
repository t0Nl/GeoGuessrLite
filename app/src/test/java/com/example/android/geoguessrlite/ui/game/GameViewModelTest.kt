package com.example.android.geoguessrlite.ui.game

import android.app.Application
import android.os.SystemClock.sleep
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.*

@RunWith(AndroidJUnit4::class)
class GameViewModelTest {
    private lateinit var appContext: Application
    private lateinit var gameViewModel: GameViewModel

    /**
     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
     * at this step we will initialize Koin related code to be able to use it in out testing.
     */
    @Before
    fun init() {
        appContext = getApplicationContext()
        gameViewModel = GameViewModel(appContext)
    }

    @Test
    fun initTest() {
        Assert.assertEquals(gameViewModel.gameScore.value, 0L)
        Assert.assertEquals(gameViewModel.guessPoints.value, 0)
        Assert.assertEquals(gameViewModel.currentTime.value, 60L)
        Assert.assertEquals(gameViewModel.guessCompleted.value, false)

        Assert.assertEquals(gameViewModel.getGameType(), GameCategory.WORLD)
        Assert.assertEquals(gameViewModel.getGameDuration(), GameDuration.ONE_MINUTE)
    }

    @Test
    fun locationGuessedTest() {
        val testDistanceFromTarget = 30000f

        gameViewModel.startTimer()
        gameViewModel.locationGuessed(testDistanceFromTarget)

        Assert.assertEquals(gameViewModel.gameScore.value, 209L)
        Assert.assertEquals(gameViewModel.guessPoints.value, 209)
        Assert.assertEquals(gameViewModel.guessCompleted.value, true)

        gameViewModel.locationGuessed(testDistanceFromTarget)

        Assert.assertEquals(gameViewModel.gameScore.value, 418L)
        Assert.assertEquals(gameViewModel.guessPoints.value, 209)
        Assert.assertEquals(gameViewModel.guessCompleted.value, true)
    }

    @Test
    fun startNextLocationTest() {
        val testDistanceFromTarget = 30000f

        gameViewModel.startTimer()
        gameViewModel.locationGuessed(testDistanceFromTarget)

        Assert.assertEquals(gameViewModel.guessCompleted.value, true)

        gameViewModel.startNextLocation()

        Assert.assertEquals(gameViewModel.guessCompleted.value, false)
    }

    @Test
    fun streetViewLoadedTest() {
        Assert.assertEquals(gameViewModel.streetViewLoaded.value, false)

        gameViewModel.streetViewLoaded()

        Assert.assertEquals(gameViewModel.streetViewLoaded.value, true)
    }

    @Test
    fun setGameTypeTest() {
        Assert.assertEquals(gameViewModel.getGameType(), GameCategory.WORLD)

        gameViewModel.setGameType(GameCategory.EUROPE)

        Assert.assertEquals(gameViewModel.getGameType(), GameCategory.EUROPE)
    }

    @Test
    fun setGameDurationTest() {
        Assert.assertEquals(gameViewModel.getGameDuration(), GameDuration.ONE_MINUTE)

        gameViewModel.setGameDuration(180)

        Assert.assertEquals(gameViewModel.getGameDuration(), GameDuration.THREE_MINUTES)
    }
}