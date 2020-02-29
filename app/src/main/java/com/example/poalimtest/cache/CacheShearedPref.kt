package com.example.poalimtest.cache

import android.content.Context

private const val PREFERENCE_NAME = "cachePref"
private const val CACHE_KEY = "cacheKey"

class CacheShearedPref(context: Context) {

    private val sharedPreference =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getIsCacheServiceStarted(): Boolean {
        return sharedPreference.getBoolean(CACHE_KEY, false)
    }

    fun setCacheServiceStarted(isStarted: Boolean) {
        val editor = sharedPreference.edit()
        editor.putBoolean(CACHE_KEY, isStarted)
        editor.apply()
    }
}