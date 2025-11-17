package com.example.android.geoguessrlite

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.geoguessrlite.database.GameCategory
import com.example.android.geoguessrlite.database.GameDuration
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.android.geoguessrlite", appContext.packageName)

        ActivityScenario.launch(MainActivity::class.java)

        onView(isRoot()).perform(waitFor(5000))

        onView(withId(R.id.leaderboardsButton)).perform(ViewActions.click())

        onView(isRoot()).perform(waitFor(5000))
    }

    @Test
    fun titleScreenTest() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.gameTitle)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.leaderboardsButton)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.selectGameType)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.selectGameDuration)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.startGameButton)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun leaderboardScreenTest() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.leaderboardsButton)).perform(ViewActions.click())

        onView(withId(R.id.option_list)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.selectGameType)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.selectGameDuration)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun gameTypeScreenTest() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.selectGameType)).perform(ViewActions.click())

        onView(withText(GameCategory.WORLD.label)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(GameCategory.NORTH_AMERICA.label)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(GameCategory.SOUTH_AMERICA.label)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(GameCategory.AUSTRALIA_PACIFIC.label)).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
        onView(withText(GameCategory.EUROPE.label)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(GameCategory.AFRICA.label)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(GameCategory.ASIA.label)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun gameDurationScreenTest() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.selectGameDuration)).perform(ViewActions.click())

        onView(withText(GameDuration.ONE_MINUTE.label)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(GameDuration.THREE_MINUTES.label)).check(ViewAssertions.matches(isDisplayed()))
        onView(withText(GameDuration.FIVE_MINUTES.label)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun playOneGameTest() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.startGameButton)).perform(ViewActions.click())

        onView(withId(R.id.mapButton)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.mapButton)).perform(ViewActions.click())

        onView(isRoot()).perform(waitFor(30000))

        onView(withId(R.id.map)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.map)).perform(ViewActions.click())

        onView(isRoot()).perform(waitFor(5000))

        onView(withId(R.id.guessButton)).perform(ViewActions.click())

        onView(isRoot()).perform(waitFor(5000))

        onView(withId(R.id.guessButton)).perform(ViewActions.click())

        onView(isRoot()).perform(waitFor(30000))

        onView(withId(R.id.categoryLabel)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.durationLabel)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.finalScoreLabel)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.returnToTitleButton)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.returnToTitleButton)).perform(ViewActions.click())

        onView(withId(R.id.gameTitle)).check(ViewAssertions.matches(isDisplayed()))
    }

    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}