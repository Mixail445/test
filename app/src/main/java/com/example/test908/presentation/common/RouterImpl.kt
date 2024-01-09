package com.example.test908.presentation.common


import androidx.fragment.app.Fragment
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

    override fun initForFragment(fragment: Fragment) {
        val navHostFragment = NavHostFragment.findNavController(fragment)
        navController = navHostFragment
    }

    override fun clear() {
        navController = null
    }
    override fun navigateTo(screen: Screens) {
        when (screen) {
            Screens.Critics -> moveToFragmentCritic()
            Screens.Reviews -> moveToFragmentReview()
            Screens.DetailReview -> moveToDetailReview()
            Screens.ToToolbarNav -> toToolbarAction()
        }
    }

    override fun toToolbarAction() {
   navController?.navigate(R.id.toolbar_nav)
    }

    private fun moveToFragmentReview() {
        navController?.navigate(R.id.reviewsFragment)
    }
    private fun moveToFragmentCritic() {
        navController?.navigate(R.id.criticFragment)
    }
    private fun moveToDetailReview() {
        navController?.navigate(R.id.detailReviewFragment)
    }
    override fun back() {
        navController?.popBackStack()
    }
    private fun NavController.navigateBackStack(id: Int) {
        if (previousBackStackEntry?.id != null) {
            popBackStack(id, false)
        } else {
            navigate(id)
        }

    }
}
