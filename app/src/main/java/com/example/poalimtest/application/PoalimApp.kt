package com.example.poalimtest.application

import android.app.Application
import com.example.poalimtest.cache.CacheManager

@Suppress("unused")
class PoalimApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initCacheManager()
    }

    private fun initKoin() = KoinStarter().start(this)

    private fun initCacheManager() = CacheManager(this).init()
}