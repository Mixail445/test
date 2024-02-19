package com.example.test908.domain.repository.review

import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.AppResult
import kotlinx.coroutines.flow.Flow


interface ReviewRepository {
    suspend fun getReviewsRemote(): AppResult<List<Review?>, Throwable>
    suspend fun fetchReviews(): Flow<List<Review>>
    suspend fun fetchReviewsById(id: String): Review
}
