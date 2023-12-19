package com.example.test908.presentation.reviews

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface ReviewsView {
    @Parcelize
    data class Model(
        val isLoading: Boolean = false,
        val reviewItems: List<ReviewUi> = emptyList(),
        val date: String,
        val isClearDateIconVisible: Boolean = false,
        val query: String,
        val timer: String?
    ) : Parcelable

    sealed interface Event {
        data object OnCalendarClearDateClick : Event
        data object RefreshReviews : Event
        data class OnQueryReviewsTextUpdated(val value: String) : Event
        data object OnCalendarClick : Event
        data object OnReviewClick : Event
        data class OnUserSelectPeriod(val firstDate: Long, val secondDate: Long) : Event
    }

    sealed interface UiLabel {
        data class ShowDatePicker(val date: Long?) : UiLabel
        data class ShowError(val title: String?, val message: String) : UiLabel
    }
}
