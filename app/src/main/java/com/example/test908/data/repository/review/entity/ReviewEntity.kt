package com.example.test908.data.repository.review.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val abstracts: String? = null,
    val byline: String? = null,
    val multimedia: String? = null,
    val publishedDate: String? = null,
    val title: String? = null,
)
