package com.example.test908.presentation.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


interface Router {
    fun init(supportFragmentManager: FragmentManager)
    fun initForFragment(fragment: Fragment)
    fun clear()
    fun navigateTo(screen: Screens)
    fun toToolbarAction()
    fun back()
}

