package com.example.test908.data.network

import com.example.test908.Constant
import com.example.test908.data.repository.ReviewRepositoryImpl
import com.example.test908.data.repository.review.remote.DispatchersProvider
import com.example.test908.data.repository.review.remote.DispatchersProviderImpl
import com.example.test908.data.repository.review.remote.ReviewApi
import com.example.test908.data.repository.review.remote.ReviewRemoteSourceImpl
import com.example.test908.domain.repository.review.ReviewRemoteSource
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.utils.ErrorHandel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideReviewApi(retrofit: Retrofit): ReviewApi = retrofit.create(ReviewApi::class.java)

    @Singleton
    @Provides
    fun provideReviewsRemoteSource(
        api: ReviewApi,
        dispatchersProvider: DispatchersProvider
    ): ReviewRemoteSource = ReviewRemoteSourceImpl(api, dispatchersProvider)

    @Singleton
    @Provides
    fun provideDispatcher(): DispatchersProvider {
        return DispatchersProviderImpl
    }

    @Singleton
    @Provides
    fun provideReviewRepository(reviewRepositoryImpl: ReviewRepositoryImpl): ReviewRepository = reviewRepositoryImpl
    @Singleton
    @Provides
    fun provideErrorHandler(): ErrorHandel = ErrorHandel()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constant.BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(RequestInterceptor())
        .addInterceptor(interceptor)
        .build()

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
