package com.example.test908.presentation.main

import androidx.fragment.app.FragmentManager


interface Router {
    fun init(supportFragmentManager: FragmentManager)
    fun clear()
    fun navigateTo(screen: Screens)
    fun back()
}

