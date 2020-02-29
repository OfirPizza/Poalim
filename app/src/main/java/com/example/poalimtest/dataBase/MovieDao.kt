package com.example.poalimtest.dataBase

import androidx.room.*
import com.example.poalimtest.dataBase.entity.FavoriteMovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(favoriteMovieItem: FavoriteMovieEntity)

    @Delete
    suspend fun removeFavoriteMovie(favoriteMovieItem: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: String):FavoriteMovieEntity?

    @Transaction
    suspend fun isMovieFavorite(movieId: String): Boolean {
        return getMovieById(movieId) != null
    }

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>
}