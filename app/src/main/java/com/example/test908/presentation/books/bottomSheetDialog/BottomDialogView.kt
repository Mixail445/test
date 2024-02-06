package com.example.test908.presentation.books.bottomSheetDialog

interface BottomDialogView {
    data class Model(
        val promoCodeETVisibility: Boolean,
        val checkBoxIsActive: Boolean
    )
    sealed interface Event {
        data class OnClickBottom(val promoCode: String) : Event
        data object OnClickCheckBox : Event
    }
    sealed interface UiLabel {
        data object CloseDialog : UiLabel
    }
}
