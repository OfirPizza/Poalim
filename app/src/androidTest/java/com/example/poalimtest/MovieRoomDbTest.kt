package com.example.poalimtest

import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.example.poalimtest.dataBase.MovieDao
import com.example.poalimtest.dataBase.RoomMovieDb
import com.example.poalimtest.dataBase.entity.FavoriteMovieEntity
import com.example.poalimtest.ui.mainActivity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieRoomDbTest {

    private lateinit var roomMovieDao: MovieDao
    private lateinit var mock: FavoriteMovieEntity

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        val build = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            RoomMovieDb::class.java
        ).build()

        roomMovieDao = build.roomMovieDao()
        mock = getMock()
    }

    @Test
    fun saveMovieAsFavorite() {
        runBlocking(IO) {
            roomMovieDao.insertOrUpdate(mock)
            assert(roomMovieDao.isMovieFavorite(mock.id))
        }
    }

    @Test
    fun removeMovieFromFavorite() {
        CoroutineScope(IO).launch {
            roomMovieDao.insertOrUpdate(mock)
            roomMovieDao.removeFavoriteMovie(mock)
            assert(!roomMovieDao.isMovieFavorite(mock.id))
        }
    }

    @Test
    fun getFavoriteMovies() {
        CoroutineScope(IO).launch {
            roomMovieDao.insertOrUpdate(mock)
            assert(roomMovieDao.getFavoriteMovies().isNotEmpty())
        }
    }

    @After
    fun clear() {
        runBlocking(IO) {
            roomMovieDao.removeFavoriteMovie(mock)
        }
    }

    private fun getMock(): FavoriteMovieEntity {
        return FavoriteMovieEntity("", "", "", 0.0, "", "", false)
    }
}
