package com.example.test908.data.repository.review.local

import androidx.room.TypeConverter
import com.example.test908.data.repository.review.entity.ReviewEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ReviewEntityConverter {
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
