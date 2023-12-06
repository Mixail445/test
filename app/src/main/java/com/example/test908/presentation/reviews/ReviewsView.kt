package com.example.test908.presentation.reviews

interface ReviewsView {
    data class Model(
        val isLoading: Boolean = false,
        val reviewItems: List<ReviewUi> = listOf(),
        val date: String,
        val isClearDateIconVisible: Boolean = false,
        val query: String
    )

    sealed interface Event {
        data object OnCalendarClearDateClick : Event
        data object RefreshReviews : Event
        data class OnQueryReviewsTextUpdated(val value: String) : Event
        data object OnCalendarClick : Event
        data object OnReviewClick : Event
        data class OnUserSelectDate(val date: Long) : Event
    }

    sealed interface UiLabel {
        data class ShowDatePicker(val date: Long?) : UiLabel
        data class ShowError(val title: String?, val message: String) : UiLabel
    }
}
