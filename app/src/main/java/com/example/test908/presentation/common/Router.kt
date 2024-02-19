package com.example.test908.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView

interface Router {
    fun init(
        fragment: Fragment,
        fragmentManager: FragmentManager,
    )

    fun initWithTabs(
        tabElementView: NavigationBarView,
        fragment: Fragment,
        fragmentManager: FragmentManager,
    )

    fun clear()

    fun navigateTo(
        screen: Screens,
        bundle: Bundle? = null,
    )

    fun back()
}
