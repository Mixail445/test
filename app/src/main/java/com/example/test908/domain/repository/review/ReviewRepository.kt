package com.example.test908.domain.repository.review

import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.AppResult
import javax.inject.Singleton


interface ReviewRepository {
    suspend fun getReviews(): AppResult<List<Review>, Throwable>
}
