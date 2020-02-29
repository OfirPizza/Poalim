package com.example.poalimtest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.poalimtest.ui.favoriteMovies.adapter.FavoriteMovieAdapter
import com.example.poalimtest.ui.mainActivity.MainActivity
import com.example.poalimtest.ui.movieList.model.ImageUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteMovieFragmentTest {

    private lateinit var mockList: List<MovieUiItemModel>
    private lateinit var movieAdapter: FavoriteMovieAdapter

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.navigation_favorite_movies)).perform(click())
        movieAdapter = FavoriteMovieAdapter()
        mockList = getMockList()

    }

    @Test
    fun testAdapter() {
        movieAdapter.updateData(mockList)
        assert(movieAdapter.itemCount > 0)
    }

    @Test
    fun testOnFavoriteMovieClick() {
        movieAdapter.updateData(mockList)
        movieAdapter.onClickListener?.invoke(mockList[0])
        onView(withId(R.id.favorite_movies_container)).check(matches(hasChildCount(1)))
    }

    private fun getMockList(): List<MovieUiItemModel> {
        return listOf(MovieUiItemModel(ImageUiModel(""), "", "", 0.0, "", "", false))
    }
}
