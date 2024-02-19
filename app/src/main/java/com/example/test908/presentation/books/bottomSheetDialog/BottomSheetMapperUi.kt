package com.example.test908.presentation.books.bottomSheetDialog

import android.content.Context
import com.example.test908.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BottomSheetMapperUi
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) {
        val body = context.resources.getString(R.string.title_text_bottom_sheet)
        val title = context.resources.getString(R.string.body_text_bottom_sheet)
        val textBottom = context.resources.getString(R.string.bottom_text_bottom_sheet)
        val editText = context.resources.getString(R.string.edit_text_bottom_sheet)
    }
