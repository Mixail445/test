package com.example.test908.data.repository.review.entity

import androidx.room.PrimaryKey

data class ReviewResponseEntity(
    val copyright: String ? = null,
    val lastUpdated: String ? = null,
    val numResults: Int? = null,
    val results: List<ReviewEntity> = emptyList(),
    val section: String ? = null,
    val status: String ? = null
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0 }


