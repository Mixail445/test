package com.example.test908.presentation

import com.example.test908.presentation.reviewList.StoryUi

data class ReviewUiState(
    val reviewItems: List<StoryUi> = listOf(),
    val reviewFilter: List<StoryUi> = listOf(),
    val reviewRefresh: List<StoryUi> = listOf(),
    val group: Boolean = true,
    val loading: Boolean = true
)
