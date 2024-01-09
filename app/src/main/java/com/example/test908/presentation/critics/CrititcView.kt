package com.example.test908.presentation.critics

import com.example.test908.presentation.common.Screens

class CriticView {

    sealed interface Event {
        data object OnClickReview : Event
    }
    sealed interface UiLabel {
        data class ShowReview(val screens: Screens) : UiLabel
    }
}
