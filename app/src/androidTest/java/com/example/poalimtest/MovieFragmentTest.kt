package com.example.poalimtest

import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.poalimtest.dataBase.MovieDao
import com.example.poalimtest.dataBase.RoomMovieDb
import com.example.poalimtest.dataBase.entity.FavoriteMovieEntity
import com.example.poalimtest.ui.mainActivity.MainActivity
import com.example.poalimtest.ui.movieList.adapter.MovieAdapter
import com.example.poalimtest.ui.movieList.model.ImageUiModel
import com.example.poalimtest.ui.movieList.model.MovieUiItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieFragmentTest {

    private lateinit var mockList: List<MovieUiItemModel>
    private lateinit var roomMovieDao: MovieDao
    private lateinit var movieAdapter: MovieAdapter

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        val build = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            RoomMovieDb::class.java
        ).build()
        roomMovieDao = build.roomMovieDao()
        onView(withId(R.id.navigation_movies)).perform(click())
        movieAdapter = MovieAdapter()
        mockList = getMockList()

    }

    @Test
    fun testAdapter() {
        movieAdapter.updateData(mockList)
        assert(movieAdapter.itemCount > 0)
    }

    @Test
    fun testOnFavoriteClick() {
        movieAdapter.updateData(mockList)
        movieAdapter.onLikeClickListener?.invoke(mockList[0])
        assert(mockList[0].isFavorite)
    }

    @Test
    fun testOnMovieClick() {
        movieAdapter.updateData(mockList)
        movieAdapter.onClickListener?.invoke(mockList[0])
        onView(withId(R.id.movie_list_container)).check(matches(hasChildCount(1)))
    }

    @After
    fun clear() {
        CoroutineScope(IO).launch {
            roomMovieDao.removeFavoriteMovie(mockList[0].toFavoriteMovieEntity)
        }

    }

    private fun getMockList(): List<MovieUiItemModel> {
        return listOf(MovieUiItemModel(ImageUiModel(""), "=", "", 0.0, "", "", false))
    }

    private val MovieUiItemModel.toFavoriteMovieEntity: FavoriteMovieEntity
        get() = FavoriteMovieEntity(
            id = this.id,
            imageUrl = this.movieImage.url,
            title = this.title,
            rating = this.rating,
            description = this.description,
            date = this.date,
            isFavorite = true
        )
}
