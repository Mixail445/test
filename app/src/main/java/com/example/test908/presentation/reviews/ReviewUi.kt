package com.example.test908.presentation.reviews

import com.example.test908.presentation.common.BaseItem

data class ReviewUi(
    override val itemId: String,
    val abstract: String,
    val byline: String,
    val date: String,
    val title: String,
    val pictureSrc: String
) : BaseItem
