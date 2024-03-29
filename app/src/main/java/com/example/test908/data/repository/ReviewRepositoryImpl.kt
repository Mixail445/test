package com.example.test908.data.repository

import com.example.test908.domain.repository.review.ReviewLocalSource
import com.example.test908.domain.repository.review.ReviewRemoteSource
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.domain.repository.review.model.Review
import com.example.test908.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewRepositoryImpl
    @Inject
    constructor(
        private val remoteSource: ReviewRemoteSource,
        private val wrapper: ResultWrapper,
        private val localSource: ReviewLocalSource,
    ) : ReviewRepository {
        override suspend fun getReviewsRemote() =
            wrapper.wrap {
                val mapped = remoteSource.getReviews().results.map { it.mapToEntity() }
                localSource.refreshReview(mapped)
                mapped.map { it.mapToDomain() }
            }

        override suspend fun fetchReviews(): Flow<List<Review>> =
            localSource.getReviews().map { localReviews ->
                localReviews.map { it.mapToDomain() }
            }

        override suspend fun fetchReviewsById(id: String) = localSource.getDataById(id.toInt()).mapToDomain()
    }
