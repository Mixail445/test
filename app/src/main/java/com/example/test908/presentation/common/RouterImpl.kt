package com.example.test908.presentation.common


import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.test908.R
import javax.inject.Inject


class RouterImpl @Inject constructor(private val controller: Int) : Router {
    private var navController: NavController? = null
    
    override fun init(fragmentManager: FragmentManager) {
        val navHostFragment = fragmentManager.findFragmentById(controller) as NavHostFragment
        navController = navHostFragment.navController
    }
    override fun clear() {
        navController = null
    }
    override fun navigateTo(screen: Screens, bundle: Bundle?) {
        when (screen) {
            Screens.Books -> moveToFragmentBooks()
            Screens.Reviews -> moveToFragmentReview()
            Screens.DetailReview -> moveToDetailReview(bundle)
            Screens.HomeFragment -> moveToHomeFragment()
        }
    }

    private fun moveToFragmentReview() {
        navController?.navigate(R.id.reviewsFragment)
    }
    private fun moveToFragmentBooks() {
        navController?.navigate(R.id.booksFragment)
    }
    private fun moveToDetailReview(bundle: Bundle?) {
        navController?.navigate(R.id.detailReviewFragment, bundle)
    }
    private fun moveToHomeFragment() {
        navController?.navigate(R.id.homeFragment)
    }
    override fun back() {
        navController?.popBackStack()
    }
}
