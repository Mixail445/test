package com.example.test908.data.repository.review.remote

import com.example.test908.domain.repository.review.ReviewRemoteSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRemoteSourceImpl (
    private val api: ReviewApi,
    private val dispatchersProvider: DispatchersProvider
) : ReviewRemoteSource {

    override suspend fun getReviews() = withContext(dispatchersProvider.io) {
        api.getReviews()
    }
}

interface DispatchersProvider {
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
    val io: CoroutineDispatcher
}

object DispatchersProviderImpl : DispatchersProvider {
    override val default = Dispatchers.Default
    override val main = Dispatchers.Main.immediate
    override val unconfined = Dispatchers.Unconfined
    override val io = Dispatchers.IO
}
