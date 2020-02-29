package com.example.poalimtest.dataBase.di

import androidx.room.Room
import com.example.poalimtest.dataBase.RoomMovieDb
import org.koin.dsl.module

private const val DATABASE = "favorite_movies"

val roomModule = module {
    single { Room.databaseBuilder(get(), RoomMovieDb::class.java, DATABASE).build() }
    single { get<RoomMovieDb>().roomMovieDao() }
}