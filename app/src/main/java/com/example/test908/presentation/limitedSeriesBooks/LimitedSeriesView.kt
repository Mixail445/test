package com.example.test908.presentation.limitedSeriesBooks

import com.example.test908.presentation.common.BaseItem
import com.example.test908.presentation.common.Screens

interface LimitedSeriesView {
    data class Model(
        val items: List<BaseItem> = emptyList(),
    )

    sealed interface Event {
        data object OnClickBackButton : Event
    }

    sealed interface UiLabel {
        data class ShowHomeFragment(val screens: Screens) : UiLabel
    }
}
