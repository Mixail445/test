package com.example.test908.presentation.reviewList

data class NumResultUi(
    val copyright: String,
    val lastUpdated: String,
    val numResults: Int,
    val results: List<StoryUi>,
    val section: String,
    val status: String
)
