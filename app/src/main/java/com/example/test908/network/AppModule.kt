package com.example.test908.network

import com.example.test908.BuildConfig
import com.example.test908.data.remote.ReviewsServiceRetrofit
import com.example.test908.data.repository.ReviewReviewRepositoryImpl
import com.example.test908.domain.repository.ReviewRepository
import com.example.test908.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): ReviewsServiceRetrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(provideBaseUrl())
            .client(okHttpClient)
            .build()
            .create(ReviewsServiceRetrofit::class.java)

    @Provides
    @Singleton
    fun provideBaseUrl() = Constant.BASE_URL

    @Singleton
    @Provides
    fun provideRemote(service: ReviewsServiceRetrofit): ReviewRepository =
        ReviewReviewRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }
}