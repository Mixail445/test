package com.example.test908.data.repository.books.featureFlag

import com.example.test908.data.repository.books.featureFlag.remote.model.FeatureFlagsDto
import javax.inject.Inject

class FeatureFlagApiImpl @Inject constructor() : FeatureFlagApi {
    override suspend fun isLimitedSeriesEnable(): FeatureFlagsDto {
        return FeatureFlagsDto(true)
    }
}
