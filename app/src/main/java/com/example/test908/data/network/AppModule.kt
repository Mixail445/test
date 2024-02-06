package com.example.test908.data.network

import android.content.Context
import com.example.test908.Constant.BASE_URL
import com.example.test908.R
import com.example.test908.data.repository.BooksRepositoryImpl
import com.example.test908.data.repository.FeatureFlagRepositoryImpl
import com.example.test908.data.repository.LimitedSeriesRepositoryImpl
import com.example.test908.data.repository.ReviewRepositoryImpl
import com.example.test908.data.repository.books.featureFlag.FeatureFlagApi
import com.example.test908.data.repository.books.featureFlag.FeatureFlagApiImpl
import com.example.test908.data.repository.books.featureFlag.remote.FeatureFlagRemoteSourceImpl
import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesApi
import com.example.test908.data.repository.books.limitedSeries.LimitedSeriesApiImpl
import com.example.test908.data.repository.books.limitedSeries.remote.LimitedSeriesRemoteSourceImpl
import com.example.test908.data.repository.books.remote.BooksApi
import com.example.test908.data.repository.books.remote.BooksRemoteSourceImpl
import com.example.test908.data.repository.review.local.ReviewDao
import com.example.test908.data.repository.review.local.ReviewLocalSourceImpl
import com.example.test908.data.repository.review.remote.DispatchersProvider
import com.example.test908.data.repository.review.remote.DispatchersProviderImpl
import com.example.test908.data.repository.review.remote.ReviewApi
import com.example.test908.data.repository.review.remote.ReviewRemoteSourceImpl
import com.example.test908.domain.repository.books.BooksRemoteSource
import com.example.test908.domain.repository.books.BooksRepository
import com.example.test908.domain.repository.featureFlag.FeatureFlagRemoteSource
import com.example.test908.domain.repository.featureFlag.FeatureFlagRepository
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRemoteSource
import com.example.test908.domain.repository.limitedSeries.LimitedSeriesRepository
import com.example.test908.domain.repository.review.ReviewLocalSource
import com.example.test908.domain.repository.review.ReviewRemoteSource
import com.example.test908.domain.repository.review.ReviewRepository
import com.example.test908.presentation.common.Router
import com.example.test908.presentation.common.RouterImpl
import com.example.test908.presentation.reviews.FavoriteData
import com.example.test908.utils.ErrorHandel
import com.example.test908.utils.FavoriteLocalSourceImpl
import com.example.test908.utils.FavoriteLocalSourceInt
import com.example.test908.utils.SharedPreferenceUtil
import com.example.test908.utils.SharedPreferencesOne
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
    fun provideLimitedRemoteSource(
        api: LimitedSeriesApi,
        dispatchersProvider: DispatchersProvider
    ): LimitedSeriesRemoteSource = LimitedSeriesRemoteSourceImpl(api, dispatchersProvider)

    @Singleton
    @Provides
    fun provideFeatureFlagRemoteSource(
        api: FeatureFlagApi,
        dispatchersProvider: DispatchersProvider
    ): FeatureFlagRemoteSource = FeatureFlagRemoteSourceImpl(api, dispatchersProvider)

    @Singleton
    @Provides
    fun provideReviewsLocalSource(
        dao: ReviewDao,
        dispatchersProvider: DispatchersProvider
    ): ReviewLocalSource = ReviewLocalSourceImpl(dao, dispatchersProvider)

    @Singleton
    @Provides
    @Named("Host")
    fun provideRouter(): Router = RouterImpl(R.id.NavHostFragment)

    @Singleton
    @Provides
    @Named("Child")
    fun provideRouterChild(): Router = RouterImpl(R.id.childNavHostFragmentOne)

    @Singleton
    @Provides
    fun provideSharePref(@ApplicationContext context: Context): SharedPreferencesOne<FavoriteData> = SharedPreferenceUtil(
        context,
        FavoriteData::class.java
    )

    @Singleton
    @Provides
    fun provideShare(sharedPreferencesOne: SharedPreferencesOne<FavoriteData>): FavoriteLocalSourceInt = FavoriteLocalSourceImpl(
        sharedPreferencesOne
    )

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
    fun provideLimitedSeriesRepository(limitedSeriesRepositoryImpl: LimitedSeriesRepositoryImpl): LimitedSeriesRepository = limitedSeriesRepositoryImpl

    @Singleton
    @Provides
    fun provideErrorHandler(@ApplicationContext context: Context): ErrorHandel = ErrorHandel(
        context
    )

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    private val moshi =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
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

    @Singleton
    @Provides
    fun provideBooksRepository(booksRepositoryImpl: BooksRepositoryImpl): BooksRepository = booksRepositoryImpl

    @Singleton
    @Provides
    fun provideFeatureFlagRepository(featureFlagRepositoryImpl: FeatureFlagRepositoryImpl): FeatureFlagRepository = featureFlagRepositoryImpl


    @Singleton
    @Provides
    fun provideBooksRemoteSource(
        api: BooksApi,
        dispatchersProvider: DispatchersProvider
    ): BooksRemoteSource = BooksRemoteSourceImpl(api, dispatchersProvider)

    @Singleton
    @Provides
    fun provideBooksApi(retrofit: Retrofit): BooksApi = retrofit.create(BooksApi::class.java)

    @Singleton
    @Provides
    fun provideFeatureFlagApi(featureFlagApiImpl: FeatureFlagApiImpl): FeatureFlagApi = featureFlagApiImpl

    @Singleton
    @Provides
    fun provideLimitedSeriesApi(limitedSeriesApiImpl: LimitedSeriesApiImpl): LimitedSeriesApi = limitedSeriesApiImpl
}
