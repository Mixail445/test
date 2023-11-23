package com.example.test908.presentation.reviewList

data class StoryUi(
    val abstract: String,
    val byline: String,
    val multimedia: List<MultimediaUi>,
    val publishedDate: String,
    val title: String
)
