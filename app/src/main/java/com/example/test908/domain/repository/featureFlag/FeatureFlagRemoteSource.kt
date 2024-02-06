package com.example.test908.domain.repository.featureFlag

import com.example.test908.data.repository.books.featureFlag.remote.model.FeatureFlagsDto

interface FeatureFlagRemoteSource {
    suspend fun status(): FeatureFlagsDto
}
