package com.example.poalimtest.ui.favoriteMovies.di

import com.example.poalimtest.ui.favoriteMovies.FavoriteMovieRepo
import com.example.poalimtest.ui.favoriteMovies.FavoriteMovieRepoImpl
import com.example.poalimtest.ui.favoriteMovies.FavoriteMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteMovieModule = module {

    factory<FavoriteMovieRepo> {
        FavoriteMovieRepoImpl(get())
    }

    viewModel { FavoriteMoviesViewModel(get()) }
}