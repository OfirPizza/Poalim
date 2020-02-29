package com.example.poalimtest.ui.movieList.di

import com.example.poalimtest.ui.movieList.MovieRepo
import com.example.poalimtest.ui.movieList.MovieRepoImpl
import com.example.poalimtest.ui.movieList.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {
    factory<MovieRepo> {
        MovieRepoImpl(get(), get())
    }
    viewModel { MovieViewModel(get(),get()) }
}