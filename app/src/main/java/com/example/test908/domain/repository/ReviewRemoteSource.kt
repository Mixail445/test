package com.example.test908.domain.repository

import com.example.test908.domain.model.NumResult
import com.example.test908.utils.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ReviewRemoteSource @Inject constructor(private val repository: ReviewRepository) {
    suspend fun getStory(): Flow<Resource<NumResult>> {
        return repository.getStory() }
}
