package com.example.test908.presentation.reviews.bottomSheetReviewFilter

interface BottomSheetReviewFilterView {
    data class Model(
        val checkBoxFavorite: Boolean,
        val checkBoxByAscending: Boolean,
        val checkBoxByDescending: Boolean,
    )

    sealed interface Event {
        data object ClickOnCheckBoxFavorite : Event

        data object ClickOnCheckBoxAscending : Event

        data object ClickOnCheckBoxDescending : Event

        data object ClickApply : Event

        data object ClickRestAll : Event
    }

    sealed interface UiLabel {
        data object ShowBackFragment : UiLabel
    }
}
