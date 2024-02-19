package com.example.test908.data.repository.books.featureFlag

import com.example.test908.data.repository.books.featureFlag.remote.model.FeatureFlagsDto

/**
 * [IMITATION API]
 */
interface FeatureFlagApi {
    suspend fun isLimitedSeriesEnable(): FeatureFlagsDto
}
