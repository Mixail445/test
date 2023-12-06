package com.example.test908.presentation.reviews

import com.example.test908.presentation.common.BaseItem
import java.time.LocalDateTime

data class ReviewUi(
    override val itemId: String,
    val abstract: String,
    val byline: String,
    val publishedDate: LocalDateTime?,
    val title: String,
    val pictureSrc: String
) : BaseItem
