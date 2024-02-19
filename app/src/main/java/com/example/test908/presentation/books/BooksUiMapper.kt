package com.example.test908.presentation.books

import android.content.Context
import com.example.test908.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class
BooksUiMapper@Inject
    constructor(
        @ApplicationContext val context: Context,
    ) {
        val body = context.resources.getString(R.string.body_banner_ui)
        val title = context.resources.getString(R.string.title_banner_ui)
        val textBottom = context.resources.getString(R.string.get_started)
        val textBottomBlackBanner = context.resources.getString(R.string.limited_series)
        val textEmptyBannerBottom = context.resources.getString(R.string.text_bottom_green_banner)
    }
