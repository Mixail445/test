package com.example.test908.data.repository.review.local

import com.example.test908.data.repository.review.entity.ReviewEntity
import com.example.test908.data.repository.review.remote.DispatchersProvider
import com.example.test908.domain.repository.review.ReviewLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ReviewLocalSourceImpl(
    private val reviewDao: ReviewDao,
    private val dispatchersProvider: DispatchersProvider
) : ReviewLocalSource {
    override suspend fun getReviews(): Flow<List<ReviewEntity>> = withContext(
        dispatchersProvider.io
    ) {
        reviewDao.getReviewList()
    }

    override suspend fun insertReviewLocal(insert: List<ReviewEntity>) = withContext(
        dispatchersProvider.io
    ) {
        reviewDao.insertReviewList(insert)
    }

    override suspend fun delete() = withContext(dispatchersProvider.io) {
        reviewDao.deleteAll()
    }

    override suspend fun refreshReview(review: List<ReviewEntity>) {
        reviewDao.refreshReview(review)
    }

    override suspend fun getDataById(id: Int): ReviewEntity = reviewDao.getReviewById(id)
}


