package com.example.test908.domain.repository

import com.example.test908.domain.model.NumResult
import javax.inject.Singleton

@Singleton
interface ReviewRepository {
    suspend fun getStory(api: String): NumResult
}
