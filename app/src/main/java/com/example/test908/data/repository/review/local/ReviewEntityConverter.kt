package com.example.test908.data.repository.review.local

import androidx.room.TypeConverter
import com.example.test908.data.repository.review.entity.MultimediaEntity
import com.example.test908.data.repository.review.entity.ReviewEntity
import com.example.test908.data.repository.review.entity.ReviewResponseEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ReviewEntityConverter {

    @TypeConverter
    fun fromStringToMultimediaEntityLists(value: String): List<MultimediaEntity>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<MultimediaEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    MultimediaEntity::class.java
                )
            ).fromJson(value)

    @TypeConverter
    fun fromMultimediaEntityListTypeToStrings(multimediaEntityListType: List<MultimediaEntity>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<MultimediaEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    MultimediaEntity::class.java
                )
            ).toJson(multimediaEntityListType)



    @TypeConverter
    fun fromStringToResponseEntityList(value: String): List<ReviewResponseEntity>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<ReviewResponseEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    ReviewResponseEntity::class.java
                )
            ).fromJson(value)

    @TypeConverter
    fun fromMovieResponseListTypeToString(responseListType: List<ReviewResponseEntity>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build().adapter<List<ReviewResponseEntity>>(
                Types.newParameterizedType(
                    List::class.java,
                    ReviewResponseEntity::class.java
                )
            ).toJson(responseListType)
    @TypeConverter
    fun fromStringToReviewList(value: String): List<ReviewEntity>? =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<ReviewEntity>>(
                Types.newParameterizedType(List::class.java, ReviewEntity::class.java)
            )
            .fromJson(value)

    @TypeConverter
    fun fromReviewListTypeToString(reviewListType: List<ReviewEntity>?): String =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<ReviewEntity>>(
                Types.newParameterizedType(List::class.java, ReviewEntity::class.java)
            )
            .toJson(reviewListType)


}
