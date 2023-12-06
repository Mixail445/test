package com.example.test908.utils

import android.content.Context
import com.example.test908.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorHandel @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun handleError(e: Throwable): String {
        when (e) {
            is UnknownHostException -> context.getString(R.string.sorry_connection_error)
            else -> context.getString(R.string.sorry_something_went_wrong)
        }
        return e.message.toString()
    }
}
