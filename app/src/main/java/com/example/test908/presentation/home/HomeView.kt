package com.example.test908.presentation.home

import com.example.test908.presentation.common.Screens

class HomeView {
    sealed interface Event {
        data object OnClickReview : Event
        data object OnClickBook : Event
    }
    sealed interface UiLabel {
        data class ShowReviewScreen(val screens: Screens) : UiLabel
        data class ShowBookScreen(val screens: Screens) : UiLabel
    }
}
