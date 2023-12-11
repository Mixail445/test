package com.example.test908.data.repository.review.local

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ReviewDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = ReviewDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideMovieDao(database: ReviewDatabase) =
        database.getReview()
}
