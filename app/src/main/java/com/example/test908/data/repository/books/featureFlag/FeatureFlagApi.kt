package com.example.test908.data.repository.books.featureFlag

import com.example.test908.data.repository.books.featureFlag.remote.model.FeatureFlagsDto

interface FeatureFlagApi {
    suspend fun isLimitedSeriesEnable(): FeatureFlagsDto
}
