package com.example.test908.presentation.common

import android.os.Bundle
import androidx.fragment.app.FragmentManager


interface Router {
    fun init(fragmentManager: FragmentManager)
    fun clear()
    fun navigateTo(screen: Screens, bundle: Bundle? = null)
    fun back()
}

