package com.example.test908.domain.repository

import com.example.test908.domain.model.NumResult
import javax.inject.Inject

class ReviewRemoteSource @Inject constructor(private val repository: ReviewRepository) {
    suspend fun getStory(): NumResult {
        return repository.getStory()
    }
}
