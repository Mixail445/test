package com.example.test908.data.repository.books.featureFlag.remote

import com.example.test908.data.repository.books.featureFlag.FeatureFlagApi
import com.example.test908.data.repository.books.featureFlag.remote.model.FeatureFlagsDto
import com.example.test908.data.repository.review.remote.DispatchersProvider
import com.example.test908.domain.repository.featureFlag.FeatureFlagRemoteSource
import kotlinx.coroutines.withContext

class FeatureFlagRemoteSourceImpl(
    private val api: FeatureFlagApi,
    private val dispatchersProvider: DispatchersProvider
) : FeatureFlagRemoteSource {
    override suspend fun status(): FeatureFlagsDto = withContext(dispatchersProvider.io) {
        api.isLimitedSeriesEnable()
    }
}
