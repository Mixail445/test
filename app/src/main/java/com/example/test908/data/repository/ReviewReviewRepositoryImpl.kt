package com.example.test908.data.repository

import com.example.test908.data.model.mapIt
import com.example.test908.data.remote.ReviewsServiceRetrofit
import com.example.test908.domain.entity.NumResultEntity
import com.example.test908.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewReviewRepositoryImpl @Inject constructor(
    private val service: ReviewsServiceRetrofit
) : ReviewRepository {
    override suspend fun getStory(api: String): NumResultEntity {
        return service.getReview(api).mapIt()
    }


}