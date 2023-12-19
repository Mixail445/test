package com.example.test908.presentation.reviews

import android.os.Parcelable
import com.example.test908.presentation.common.BaseItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewUi(
    override val itemId: String,
    val abstract: String,
    val byline: String,
    val date: String,
    val title: String,
    val pictureSrc: String
) : BaseItem, Parcelable
