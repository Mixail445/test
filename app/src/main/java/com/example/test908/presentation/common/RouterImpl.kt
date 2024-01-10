package com.example.test908.presentation.common


import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.test908.R
import javax.inject.Inject


class RouterImpl @Inject constructor() : Router {
    private var navController: NavController? = null
    private var navControllerHost: NavController? = null


    override fun init(supportFragmentManager: FragmentManager) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        navControllerHost = navHostFragment.navController
    }


    override fun initForFragment(fragment: Fragment) {
        val navHostFragment = NavHostFragment.findNavController(fragment)
        navController = navHostFragment
    }

    override fun clear() {
        navController = null
        navControllerHost = null
    }
    override fun navigateTo(screen: Screens) {
        when (screen) {
            Screens.Critics -> moveToFragmentCritic()
            Screens.Reviews -> moveToFragmentReview()
            Screens.DetailReview -> moveToDetailReview()
        }
    }

    private fun moveToFragmentReview() {
        navController?.navigateBackStack(R.id.homeFragment)
    }
    private fun moveToFragmentCritic() {
        navControllerHost?.navigateBackStack(R.id.criticFragment)
    }
    private fun moveToDetailReview() {
        navControllerHost?.navigate(R.id.detailReviewFragment)
    }
    override fun back() {
        navControllerHost?.popBackStack()
    }
    private fun NavController.navigateBackStack(id: Int) {
        if (previousBackStackEntry?.id != null) {
            popBackStack(id, false)
        } else {
            navigate(id)
        }
    }
}
