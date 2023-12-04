package com.example.test908.domain.repository

import com.example.test908.domain.model.NumResult
import com.example.test908.utils.Resource
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
interface ReviewRepository {
    suspend fun getStory(): Flow<Resource<NumResult>>
}
