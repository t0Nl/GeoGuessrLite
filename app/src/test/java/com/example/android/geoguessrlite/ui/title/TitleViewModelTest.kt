package com.example.android.geoguessrlite.ui.title

import android.app.Application
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TitleViewModelTest {
    private lateinit var appContext: Application
    private lateinit var titleViewModel: TitleViewModel

    /**
     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
     * at this step we will initialize Koin related code to be able to use it in out testing.
     */
    @Before
    fun init() {
        appContext = getApplicationContext()
        titleViewModel = TitleViewModel(appContext)
    }

    @Test
    fun initTest() {
        Assert.assertEquals(titleViewModel.selectedGameType.value, GameCategory.WORLD.label)
        Assert.assertEquals(
            titleViewModel.selectedGameDuration.value,
            GameDuration.ONE_MINUTE.label
        )
    }

    @Test
    fun setGameType() {
        titleViewModel.setGameType(GameCategory.EUROPE.label)

        Assert.assertEquals(titleViewModel.selectedGameType.value, GameCategory.EUROPE.label)
    }

    @Test
    fun setGameDuration() {
        titleViewModel.setGameDuration(GameDuration.THREE_MINUTES.label)

        Assert.assertEquals(
            titleViewModel.selectedGameDuration.value,
            GameDuration.THREE_MINUTES.label
        )
    }
}