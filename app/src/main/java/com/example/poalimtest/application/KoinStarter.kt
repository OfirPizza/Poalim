package com.example.poalimtest.application

import android.app.Application
import com.example.poalimtest.cache.di.cachePrefModule
import com.example.poalimtest.connactivity.di.connectivityModule
import com.example.poalimtest.dataBase.di.roomModule
import com.example.poalimtest.network.di.retrofitModule
import com.example.poalimtest.ui.favoriteMovies.di.favoriteMovieModule
import com.example.poalimtest.ui.mainActivity.di.mainActivityModule
import com.example.poalimtest.ui.movieList.di.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinStarter {

    private var koinApp: KoinApplication? = null

    fun start(application: Application) {
        koinApp = getOrCreateKoinApplication(application)
    }

    private fun getOrCreateKoinApplication(application: Application): KoinApplication {
        return GlobalContext.getOrNull()?.apply {
            loadKoinModules(getModules())
        } ?: startKoin(application)
    }

    private fun startKoin(application: Application): KoinApplication {
        return startKoin {
            androidContext(application)
            androidLogger(Level.DEBUG)
            modules(getModules())
        }
    }

    private fun getModules() = listOf(
        roomModule,
        retrofitModule,
        cachePrefModule,
        connectivityModule,
        mainActivityModule,
        movieModule,
        favoriteMovieModule
    )
}