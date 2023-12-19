package com.example.test908.presentation.main

class MainView {
    data class Model(
        val reviewColor: Int,
        val criticColor: Int,
        val statusBarColor: Int,
        val toolbarColorText: Int,
        val toolbarBackgroundColor: Int,
        val criticBackgroundColor: Int,
        val reviewBackgroundColor: Int
        
    )

    sealed interface Event {
        data object OnClickCritic : Event
        data object OnClickReview : Event
        
    }

    sealed interface Router {
        data class NavigateTo(val fragment: Int) : Router
        data object Back : Router
    }
}
