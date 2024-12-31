package com.tta.dailytaskteamt

import androidx.multidex.MultiDexApplication
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class DailyTaskApplication : MultiDexApplication() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }

    companion object {
        var instance: DailyTaskApplication? = null
        private fun coroutineExceptionHandler() =
            instance?.coroutineExceptionHandler ?: CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }

        val IODispatcher = Dispatchers.IO + coroutineExceptionHandler()
        val MainDispatcher = Dispatchers.Main + coroutineExceptionHandler()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}