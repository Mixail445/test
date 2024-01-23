package com.example.test908.presentation.common

import android.app.Activity
import android.os.Bundle


interface Router {
    fun init(activity: Activity)
    fun clear()
    fun navigateTo(screen: Screens, bundle: Bundle? = null)
    fun back()
}

