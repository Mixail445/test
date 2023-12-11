package com.example.test908.domain.repository.review

import com.example.test908.data.repository.review.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

interface ReviewLocalSource {
    suspend fun getReviews(): Flow<List<ReviewEntity>>
    suspend fun insertReviewLocal(insert: List<ReviewEntity>)
    suspend fun delete()
    suspend fun refreshReview(review: List<ReviewEntity>)
}
