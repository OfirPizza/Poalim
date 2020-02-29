package com.example.poalimtest.ui.mainActivity.di

import com.example.poalimtest.ui.mainActivity.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainActivityModule = module {
    viewModel { MainActivityViewModel(get()) }
}