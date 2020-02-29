package com.example.poalimtest.cache

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.example.poalimtest.cache.service.ClearCacheService
import java.util.concurrent.TimeUnit


private const val JOB_ID = 101

class CacheManager(private val context: Context) {

    private val cacheShearedPref = CacheShearedPref(context)

    fun init() {
        startClearCacheService()
    }

    private fun startClearCacheService() {
        if (isServiceStarted()) return

        createJobScheduler()
    }

    private fun createJobScheduler() {
        val componentName = ComponentName(context, ClearCacheService::class.java)
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
            .setPersisted(true)
            .setPeriodic(TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES))
            .build()


        val resultCode = jobScheduler.schedule(jobInfo)
        updateCachePref(resultCode)
    }

    private fun updateCachePref(resultCode: Int) {
        cacheShearedPref.setCacheServiceStarted(resultCode == JobScheduler.RESULT_SUCCESS)
    }

    private fun isServiceStarted(): Boolean {
        return cacheShearedPref.getIsCacheServiceStarted()
    }
}