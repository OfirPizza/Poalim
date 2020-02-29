package com.example.poalimtest.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.poalimtest.dataBase.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)

abstract class RoomMovieDb : RoomDatabase() {
    abstract fun roomMovieDao(): MovieDao
}