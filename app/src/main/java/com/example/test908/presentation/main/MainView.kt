package com.example.test908.presentation.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class MainView {
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
        data object OnClickCritic : Event
        data object OnClickReview : Event
        
    }

    sealed interface UiLabel {
        data class NavigateToNext(val screen: Screens) : UiLabel
    }
}
