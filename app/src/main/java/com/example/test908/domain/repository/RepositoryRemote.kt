package com.example.test908.domain.repository


import com.example.test908.data.model.Result

import javax.inject.Singleton

@Singleton
interface RepositoryRemote {
    suspend fun getStory(api: String): Result
}