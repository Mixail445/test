package com.example.test908.presentation.main

import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.test908.R
import javax.inject.Inject

class RouterImpl @Inject constructor() : Router {

    private var navController: NavController? = null
    override fun init(supportFragmentManager: FragmentManager) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }
    override fun clear() {
        navController = null
    }
    override fun navigateTo(screen: Screens) {
        when (screen) {
            Screens.Critics -> moveToFragmentCritic()
            Screens.Reviews -> moveToFragmentReview()
        }
    }
    private fun moveToFragmentReview() {
        navController?.navigate(R.id.fragRewiewes2)
    }
    private fun moveToFragmentCritic() {
        navController?.navigate(R.id.fragCritic)
    }
    override fun back() {
        navController?.popBackStack()
    }
}
