package com.example.test908.presentation.home

import android.os.Parcelable
import com.example.test908.presentation.common.Screens
import kotlinx.parcelize.Parcelize

class HomeView {
    @Parcelize
    data class Model(
        val reviewColor: Int,
        val criticColor: Int,
        val statusBarColor: Int,
        val toolbarColorText: Int,
        val toolbarBackgroundColor: Int,
        val criticBackgroundColor: Int,
        val reviewBackgroundColor: Int

    ) : Parcelable
    sealed interface Event {
        data object OnClickReview : Event
        data object OnClickCritic : Event
    }
    sealed interface UiLabel {
        data class ShowReviewScreen(val screens: Screens) : UiLabel
        data class ShowCriticScreen(val screens: Screens) : UiLabel
    }
}
