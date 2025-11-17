package com.example.android.geoguessrlite.ui.leaderboard

import android.app.Application
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LeaderboardViewModelTest {
    private lateinit var appContext: Application
    private lateinit var leaderboardViewModel: LeaderboardViewModel

    /**
     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
     * at this step we will initialize Koin related code to be able to use it in out testing.
     */
    @Before
    fun init() {
        appContext = getApplicationContext()
        leaderboardViewModel = LeaderboardViewModel(appContext)
    }

    @Test
    fun initTest() {
        Assert.assertEquals(leaderboardViewModel.selectedGameType.value, GameCategory.WORLD.label)
        Assert.assertEquals(
            leaderboardViewModel.selectedGameDuration.value,
            GameDuration.ONE_MINUTE.label
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun setFilterParamsTest() = runTest {
        leaderboardViewModel.setFilterParams(
            GameDuration.THREE_MINUTES.label,
            GameCategory.EUROPE.label,
        )

        Assert.assertEquals(leaderboardViewModel.selectedGameType.value, GameCategory.EUROPE.label)
        Assert.assertEquals(
            leaderboardViewModel.selectedGameDuration.value,
            GameDuration.THREE_MINUTES.label
        )
    }
}