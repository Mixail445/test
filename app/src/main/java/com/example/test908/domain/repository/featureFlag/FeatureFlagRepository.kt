package com.example.test908.domain.repository.featureFlag

import com.example.test908.utils.AppResult

interface FeatureFlagRepository {
    suspend fun obtainFlags(): AppResult<FeatureFlags, Throwable>
}

data class FeatureFlags(
    val isLimitedSeriesEnabled: Boolean
)
