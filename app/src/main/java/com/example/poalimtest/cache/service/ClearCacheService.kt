package com.example.poalimtest.cache.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.bumptech.glide.Glide
import com.example.poalimtest.cache.CacheShearedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


private val TAG = ClearCacheService::class.java.simpleName

class ClearCacheService : JobService() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob")
        clearCacheJob(params)
        return true
    }

    private fun clearCacheJob(params: JobParameters?) {
        Glide.get(applicationContext).clearMemory()
        CoroutineScope(IO).launch {
            Glide.get(applicationContext).clearDiskCache()
            Log.d(TAG, "clearDiskCache")
        }
        jobFinished(params, false)
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob")
        CacheShearedPref(applicationContext).setCacheServiceStarted(false)
        return true
    }
}