package com.example.test908.presentation.main

import android.content.Context
import com.example.test908.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MainUiMapper @Inject constructor(@ApplicationContext val context: Context) {
    private val colorOrange: Int by lazy { context.getColor(R.color.orange) }
    private val colorBlue: Int by lazy { context.getColor(R.color.blue) }
    private val colorWhite: Int by lazy { context.getColor(R.color.white_2) }
    fun orangeColor() = colorOrange
    fun blueColor() = colorBlue
    fun whiteColor() = colorWhite
    fun getIdFragmentCritic() = R.id.fragCritic
    fun getIdFragmentReview() = R.id.fragRewiewes2
}
