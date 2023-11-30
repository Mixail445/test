package com.example.test908.presentation

import com.example.test908.presentation.reviewList.StoryUi

data class ReviewUiState(
    val reviewItems: List<StoryUi> = listOf(),
    val date: String = "",
    val search: String = "",
    val isLoading: Boolean = true
)
