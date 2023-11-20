package com.example.test908.data.repository

import com.example.test908.data.model.Result
import com.example.test908.domain.repository.RepositoryRemote
import com.example.test908.network.ServiceRetrofit
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val service: ServiceRetrofit
) : RepositoryRemote {
    override suspend fun getStory(api: String): Result {
        return service.getReview(api)
    }


}