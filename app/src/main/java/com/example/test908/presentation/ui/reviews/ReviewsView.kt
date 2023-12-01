package com.example.test908.presentation.ui.reviews

import com.example.test908.presentation.reviewList.ReviewUi

interface ReviewsView {
    data class Model(
        val reviewItems: List<ReviewUi> = listOf(),
        val date: String = "",
        val search: String = "",
        val isLoading: Boolean = true
    )

    sealed interface Event {
        data object RefreshReviews : Event
        data class OnQueryReviewsTextUpdated(val values: String) : Event
        data object OnCalendarClick : Event
        data object OnReviewClick : Event
        data class OnUserSelectDate(val date: String) : Event
    }

    sealed interface UiLabel {
        data object ShowCalendar : UiLabel
    }
}
