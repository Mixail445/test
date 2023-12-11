package com.example.test908.data.repository.review.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.test908.data.repository.review.entity.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviewList(review: List<ReviewEntity>)

    @Query("SELECT * FROM ${"review"}")
    fun getReviewList(): Flow<List<ReviewEntity>>

    @Query("DELETE FROM ${"review"}")
    suspend fun deleteAll()

    @Transaction
    suspend fun refreshReview(review: List<ReviewEntity>) {
        deleteAll()
        insertReviewList(review)
    }
}
