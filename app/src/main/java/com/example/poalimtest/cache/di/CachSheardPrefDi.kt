package com.example.poalimtest.cache.di

import com.example.poalimtest.cache.CacheShearedPref
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val cachePrefModule = module {
    factory { CacheShearedPref(androidContext()) }
}