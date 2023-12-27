package com.example.test908.presentation.books

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface BooksView {
    @Parcelize
    data class Model(
        val isLoading: Boolean = false,
        val booksItems: List<BooksUi> = emptyList()
    ) : Parcelable
    sealed interface Event {
        data object RefreshBooks : Event
        data class OnClickNestedRc(val url: String) : Event
    }

    sealed interface UiLabel {
        data class ShowBrowse(val uri: String) : UiLabel
    }
}
