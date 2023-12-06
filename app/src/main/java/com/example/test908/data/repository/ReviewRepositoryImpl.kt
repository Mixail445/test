package com.example.test908.data.repository

import com.example.test908.data.repository.review.model.mapToDomain
import com.example.test908.domain.repository.review.ReviewRemoteSource
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.utils.ResultWrapper
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val remoteSource: ReviewRemoteSource,
    private val wrapper: ResultWrapper
) : ReviewRepository {

    override suspend fun getReviews() = wrapper.wrap {
        val response = remoteSource.getReviews()
        val mapped = response.results.map { it.mapToDomain() }
        mapped
    }
}
