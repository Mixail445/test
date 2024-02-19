package com.example.test908.presentation.books

import com.example.test908.presentation.common.BaseItem
import com.example.test908.presentation.common.Screens

interface BooksView {
    data class Model(
        val isLoading: Boolean = false,
        val items: List<BaseItem> = emptyList(),
    )

    sealed interface Event {
        data object RefreshBooks : Event

        data class OnClickNestedRc(val url: String) : Event

        data class OnClickBannerToNextFragment(val id: String) : Event

        data class OnClickBottomBanner(val id: String) : Event

        data class OnClickBannerGreen(val id: String) : Event
    }

    sealed interface UiLabel {
        data class ShowBrowse(val uri: String) : UiLabel

        data class ShowFragmentWithDate(val screens: Screens, val id: String) : UiLabel

        data class ShowBottomSheetDialog(val screens: Screens) : UiLabel
    }
}
