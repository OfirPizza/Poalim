package com.example.poalimtest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.poalimtest.ui.mainActivity.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testNavToFavoriteMovieFragment() {
        onView(withId(R.id.navigation_favorite_movies)).perform(click())
        onView(withId(R.id.favorite_movies_container)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavToMovieFragment() {
        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.movie_list_container)).check(matches(isDisplayed()))
    }
}
