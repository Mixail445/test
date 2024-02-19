package com.example.test908.presentation.common

sealed class Screens {
    data object DetailReview : Screens()

    data object HomeFragment : Screens()

    data object DateFragment : Screens()

    data object DialogFragmentBooks : Screens()

    data object DialogFragmentReview : Screens()

    data class DialogToFragment(val asc: Boolean, val desc: Boolean, val fav: Boolean) : Screens()

    data class FragmentToDialog(val asc: Boolean, val desc: Boolean, val fav: Boolean) : Screens()
}
