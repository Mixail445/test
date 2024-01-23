package com.example.test908.presentation.common


import android.app.Activity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.test908.R
import javax.inject.Inject


class RouterImpl @Inject constructor(private val controller: Int) : Router {
    private var navController: NavController? = null
    
    override fun init(activity: Activity) {
        val navHostFragment =
            activity.findNavController(controller)
        navController = navHostFragment
    }
    override fun clear() {
        navController = null
    }
    override fun navigateTo(screen: Screens, bundle: Bundle?) {
        when (screen) {
            Screens.Critics -> moveToFragmentCritic()
            Screens.Reviews -> moveToFragmentReview()
            Screens.DetailReview -> moveToDetailReview(bundle)
            Screens.HomeFragment -> moveToHomeFragment()
        }
    }

    private fun moveToFragmentReview() {
        navController?.navigate(R.id.reviewsFragment)
    }
    private fun moveToFragmentCritic() {
        navController?.navigate(R.id.criticFragment)
    }
    private fun moveToDetailReview(bundle: Bundle?) {
        navController?.navigate(R.id.detailReviewFragment2, bundle)
    }
    private fun moveToHomeFragment() {
        navController?.navigate(R.id.homeFragment)
    }
    override fun back() {
        navController?.popBackStack()
    }
}
