package com.example.test908.domain.repository.review

import com.example.test908.data.repository.review.model.ReviewsResponseDto

interface ReviewRemoteSource {
    suspend fun getReviews(): ReviewsResponseDto
}
