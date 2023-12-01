package com.example.test908.presentation.reviewList

data class NumResultUi(
    val copyright: String,
    val lastUpdated: String,
    val numResults: Int,
    val results: List<ReviewUi>,
    val section: String,
    val status: String
)
