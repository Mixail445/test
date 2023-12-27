package com.example.test908.presentation.detailReview

import com.example.test908.presentation.common.Screens

class DetailReviewView {
    data class Model(
        val photo: String,
        val text: String
    )
    sealed interface Event {
        data object OnClickBackButton : Event
    }
    sealed interface UiLabel {
        data class ShowHomeFragment(val screens: Screens) : UiLabel
    }
}
