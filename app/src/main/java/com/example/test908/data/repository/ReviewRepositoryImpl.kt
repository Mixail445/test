package com.example.test908.data.repository

import com.example.test908.domain.repository.review.ReviewLocalSource
import com.example.test908.domain.repository.review.ReviewRemoteSource
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.ResultWrapper
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReviewRepositoryImpl @Inject constructor(
    private val remoteSource: ReviewRemoteSource,
    private val wrapper: ResultWrapper,
    private val localSource: ReviewLocalSource
) : ReviewRepository {

    override suspend fun getReviewsRemote() = wrapper.wrap {
        val mapped = remoteSource.getReviews().results.map { it.mapToEntity() }
        localSource.refreshReview(mapped)
        mapped.map { it.mapToDomain() }
    }
    override suspend fun fetchReviews(): Flow<List<Review?>> {
           return localSource.getReviews().map { it.map { it.mapToDomain() } }
        }
}
