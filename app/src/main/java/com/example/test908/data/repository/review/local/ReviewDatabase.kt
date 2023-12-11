package com.example.test908.data.repository.review.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.test908.data.repository.review.entity.ReviewEntity

@Database(
    [ReviewEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ReviewEntityConverter::class)
abstract class ReviewDatabase : RoomDatabase() {
    abstract fun getReview(): ReviewDao
    companion object {
        @Volatile
        private var INSTANCE: ReviewDatabase? = null

        fun getDatabase(context: Context): ReviewDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReviewDatabase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
