package com.example.test908.data.repository

import com.example.test908.data.dto.mapToDomain
import com.example.test908.data.remote.ReviewsServiceRetrofit
import com.example.test908.domain.model.NumResult
import com.example.test908.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewReviewRepositoryImpl @Inject constructor(
    private val service: ReviewsServiceRetrofit
) : ReviewRepository {
    override suspend fun getStory(api: String): NumResult {
        return service.getReview(api).mapToDomain()
    }
}
