package com.example.test908.presentation.reviews

import android.content.Context
import com.example.test908.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReviewUiMapper @Inject constructor(@ApplicationContext val context: Context) {

    fun mapTimer(sec: String?) = context.getString((R.string.time_passed), sec)

}
