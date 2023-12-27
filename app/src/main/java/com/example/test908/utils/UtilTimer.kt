package com.example.test908.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UtilTimer @Inject constructor() {

    private var isStarted: Boolean = false

    private val handler = CoroutineExceptionHandler { _, _ ->
        Log.d("", "TODO")
    }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + handler)

    val time = MutableLiveData<Long>()

    private var currentValue: Long = 0
    fun start(defaultValue: Long = 0) {
        isStarted = true
        currentValue = defaultValue
        scope.launch {
            while (isStarted) {
                delay(1000)
                currentValue += 1
                time.postValue(currentValue)
            }
        }
    }
    fun stop() {
        isStarted = false
    }
}

