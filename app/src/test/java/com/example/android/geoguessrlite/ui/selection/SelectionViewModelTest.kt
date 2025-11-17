package com.example.android.geoguessrlite.ui.selection

import android.app.Application
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.geoguessrlite.database.GameCategory
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SelectionViewModelTest {
    private lateinit var appContext: Application
    private lateinit var selectionViewModel: SelectionViewModel

    /**
     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
     * at this step we will initialize Koin related code to be able to use it in out testing.
     */
    @Before
    fun init() {
        appContext = getApplicationContext()
        selectionViewModel = SelectionViewModel(appContext)
    }

    @Test
    fun initTest() {
        Assert.assertEquals(selectionViewModel.finishSelection.value, null)
        Assert.assertEquals(selectionViewModel.fragmentSelectionType.value, null)
        Assert.assertEquals(selectionViewModel.fragmentSelectionOptions.value, null)
    }

    @Test
    fun setSelectionOptionsTest() {
        selectionViewModel.setSelectionOptions(SelectionType.GAME_TYPE.label)

        Assert.assertEquals(selectionViewModel.fragmentSelectionType.value, SelectionType.GAME_TYPE)
    }

    @Test
    fun onOptionClickedTest() {
        selectionViewModel.onOptionClicked(GameCategory.EUROPE.label)

        Assert.assertEquals(selectionViewModel.finishSelection.value, GameCategory.EUROPE.label)
    }
}