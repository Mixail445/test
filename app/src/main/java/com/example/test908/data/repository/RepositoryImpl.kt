package com.example.test908.data.repository


import com.example.test908.utils.Constant.API_KEY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject
constructor(private val service: RemoteData) {
    suspend fun getStory() = service.getUser(API_KEY)


}