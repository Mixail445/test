package com.example.test908.data.repository

import com.example.test908.domain.repository.featureFlag.FeatureFlagRemoteSource
import com.example.test908.domain.repository.featureFlag.FeatureFlagRepository
import com.example.test908.domain.repository.featureFlag.FeatureFlags
import com.example.test908.utils.AppResult
import com.example.test908.utils.ResultWrapper
import javax.inject.Inject

class FeatureFlagRepositoryImpl @Inject constructor(
    private val remoteSource: FeatureFlagRemoteSource,
    private val wrapper: ResultWrapper
) : FeatureFlagRepository {
    override suspend fun obtainFlags(): AppResult<FeatureFlags, Throwable> = wrapper.wrap { 
        remoteSource.status().mapToDomain()
    }
      
    
    

} 
