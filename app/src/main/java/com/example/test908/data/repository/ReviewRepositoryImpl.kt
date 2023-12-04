package com.example.test908.data.repository

import com.example.test908.data.dto.mapToDomain
import com.example.test908.data.remote.ReviewsServiceRetrofit
import com.example.test908.domain.model.NumResult
import com.example.test908.domain.repository.ReviewRepository
import com.example.test908.utils.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReviewRepositoryImpl @Inject constructor(
    private val service: ReviewsServiceRetrofit
) : ReviewRepository {
    override suspend fun getStory(): Flow<Resource<NumResult>> = doRequest {
        service.getReview().mapToDomain()
    }
    private fun <T> doRequest(
        request: suspend () -> T
    ) = flow<Resource<T>> {
        request().also { data ->
            emit(Resource.Success(data = data))
        }
    }.flowOn(Dispatchers.IO).catch { exception ->
        emit(Resource.Error(message = exception.localizedMessage ?: "Error Occurred!"))
    }
}
