package com.example.test908.domain.repository


import com.example.test908.domain.entity.NumResultEntity

import javax.inject.Singleton

@Singleton

interface ReviewRepository {
    suspend fun getStory(api: String): NumResultEntity
}