package app.krishiyog.ui.component.list

import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import app.krishiyog.DataStatus
import app.krishiyog.R
import app.krishiyog.TestUtil.dataStatus
import app.krishiyog.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

@HiltAndroidTest
class ListDataListActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mActivityTestRule = ActivityTestRule(ListActivity::class.java, false, false)
    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun displayRecipesList() {
        dataStatus = DataStatus.Success
        mActivityTestRule.launchActivity(null)
        onView(withId(R.id.rv_list_data)).check(matches(isDisplayed()))
    }

    @Test
    fun testRefresh() {
        dataStatus = DataStatus.Success
        mActivityTestRule.launchActivity(null)
        //Before refresh there is a list .
        onView(withId(R.id.rv_list_data)).check(matches(isDisplayed()))
        // do refresh .
        onView(withId(R.id.action_refresh)).perform(click())
        //after refresh there is a list.
        onView(withId(R.id.rv_list_data)).check(matches(isDisplayed()))
    }

    @Test
    fun noData() {
        dataStatus = DataStatus.Fail
        mActivityTestRule.launchActivity(null)
        onView(withId(R.id.rv_list_data)).check(matches(not(isDisplayed())))
    }


    @Test
    fun testSearch() {
        dataStatus = DataStatus.Success
        mActivityTestRule.launchActivity(null)
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
    }

    @Test
    fun testNoSearchResult() {
        dataStatus = DataStatus.EmptyResponse
        val searchText = "Any text"
        mActivityTestRule.launchActivity(null)
        sleep(LENGTH_LONG * 2L)
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
                .perform(typeText(searchText))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        sleep(Toast.LENGTH_SHORT / 2L)
    }

    @Test
    fun testScroll() {
        dataStatus = DataStatus.Success
        mActivityTestRule.launchActivity(null)
        onView(withId(R.id.rv_list_data))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }
}
