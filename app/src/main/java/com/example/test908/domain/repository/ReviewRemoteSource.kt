package com.example.test908.domain.repository

import com.example.test908.domain.entity.NumResultEntity
import javax.inject.Inject

class ReviewRemoteSource @Inject constructor(private val repository:ReviewRepository) {
    suspend fun getStory(api: String):NumResultEntity{
        return repository.getStory(api)
    }
}