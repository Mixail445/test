package com.example.test908.presentation.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.test908.R
import com.example.test908.presentation.books.BooksFragmentDirections
import com.example.test908.presentation.reviews.ReviewsFragmentDirections
import com.example.test908.presentation.reviews.bottomSheetReviewFilter.BottomSheetReviewFilterFragmentDirections
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject

class RouterImpl
    @Inject
    constructor(private val controller: Int) : Router {
        private var navController: NavController? = null

        override fun init(
            fragment: Fragment,
            fragmentManager: FragmentManager,
        ) {
            val navHostFragment = fragmentManager.findFragmentById(controller) as NavHostFragment?
            navController = navHostFragment?.navController ?: fragment.findNavController()
        }

        override fun initWithTabs(
            tabElementView: NavigationBarView,
            fragment: Fragment,
            fragmentManager: FragmentManager,
        ) {
            init(fragment, fragmentManager)
            navController?.let { NavigationUI.setupWithNavController(tabElementView, it, false) }
        }

        override fun clear() {
            navController = null
        }

        override fun navigateTo(
            screen: Screens,
            bundle: Bundle?,
        ) {
            when (screen) {
                Screens.DetailReview -> moveToDetailReview(bundle)
                Screens.HomeFragment -> moveToHomeFragment()
                Screens.DateFragment -> moveToFragmentWithDate()
                Screens.DialogFragmentBooks -> showDialogFragment()
                Screens.DialogFragmentReview -> showDialogReviewFragment()
                is Screens.DialogToFragment ->
                    dialogMoveToFragment(
                        fav = screen.fav,
                        desc = screen.desc,
                        asc = screen.asc,
                    )

                is Screens.FragmentToDialog ->
                    fragmentMoveToDialog(
                        fav = screen.fav,
                        desc = screen.desc,
                        asc = screen.asc,
                    )
            }
        }

        private fun fragmentMoveToDialog(
            fav: Boolean,
            desc: Boolean,
            asc: Boolean,
        ) {
            val action =
                ReviewsFragmentDirections.actionReviewsFragmentToBottomSheetReviewFilterFragment()
            action.byAscending = asc
            action.byDescending = desc
            action.favirite = fav
            navController?.navigate(action)
        }

        private fun dialogMoveToFragment(
            fav: Boolean,
            asc: Boolean,
            desc: Boolean,
        ) {
            val action =
                BottomSheetReviewFilterFragmentDirections.actionBottomSheetReviewFilterFragmentToReviewsFragment()
            action.favirite = fav
            action.byDescending = desc
            action.byAscending = asc
            navController?.navigate(action)
        }

        private fun showDialogReviewFragment() {
            navController?.navigate(BottomSheetReviewFilterFragmentDirections.actionBottomSheetReviewFilterFragmentToReviewsFragment())
        }

        private fun showDialogFragment() {
            navController?.navigate(BooksFragmentDirections.actionBooksFragmentToBottomSheetDialogLimitedSeriesFragment())
        }

        private fun moveToFragmentWithDate() {
            navController?.navigate(R.id.action_homeFragment_to_limitedSeriesFragment2)
        }

        private fun moveToDetailReview(bundle: Bundle?) {
            navController?.navigate(R.id.action_homeFragment_to_detailReviewFragment, bundle)
        }

        private fun moveToHomeFragment() {
            navController?.navigate(R.id.homeFragment)
        }

        override fun back() {
            navController?.popBackStack()
        }

        private fun NavController.navigateTo(id: Int) {
            if (previousBackStackEntry?.id != null) {
                popBackStack(id, false)
            } else {
                navigate(id)
            }
        }
    }
