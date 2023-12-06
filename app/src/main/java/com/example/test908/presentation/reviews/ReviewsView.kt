package com.example.test908.presentation.reviews

interface ReviewsView {
    data class Model(
        val reviewItems: List<ReviewUi> = listOf(),
        val date: String,
        val query: String,
        val isLoading: Boolean = false
    )

    sealed interface Event {
        data object RefreshReviews : Event
        data class OnQueryReviewsTextUpdated(val value: String) : Event
        data object OnCalendarClick : Event
        data object OnReviewClick : Event
        data class OnUserSelectDate(val date: Long) : Event
    }

    sealed interface UiLabel {
        // todo if previous date not selected show current, if selected show previous selected
        data object ShowDatePicker : UiLabel
        data class ShowError(val message: String) : UiLabel
    }
}
