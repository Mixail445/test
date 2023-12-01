package com.example.test908.presentation.reviewList

import com.example.test908.presentation.adapters.DelegateAdapterItem

data class ReviewUi(
    val abstract: String,
    val byline: String,
    val multimedia: List<MultimediaUi>,
    val publishedDate: String,
    val title: String
) : DelegateAdapterItem {
    override fun id(): Any {
        return title
    }

    override fun content(): Any {
        return byline
    }
}
