package com.example.poalimtest.connactivity.di

import com.example.poalimtest.connactivity.ConnectivityRepo
import com.example.poalimtest.connactivity.ConnectivityRepoImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val connectivityModule = module {
    factory<ConnectivityRepo> { ConnectivityRepoImpl(androidContext()) }
}
