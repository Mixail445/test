package com.example.test908.utils

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class UtilTimer @Inject constructor() {
    val time = MutableLiveData<String>()
    private fun timer() {
        val timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
             time.postValue((((Long.MAX_VALUE) / 1000 - millisUntilFinished / 1000).toString()))
            }
            override fun onFinish() {
                Unit
            }
        }
        timer.start()
    }
    init {
        timer()
    }

}

