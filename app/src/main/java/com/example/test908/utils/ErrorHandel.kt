package com.example.test908.utils

import java.net.UnknownHostException

class ErrorHandel {
    fun handleError(e: Throwable): String {
        when (e) {
            is UnknownHostException -> "Sorry connection error"
            else -> "Sorry something went wrong"
        }
        return e.message.toString()
    }
}