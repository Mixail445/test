package com.example.test908.data.repository.review.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var byline: String = "",
    var multimedia: String = "",
    var publishedDate: String = "",
    var shortUrl: String = "",
    var title: String = "",
    var abstract: String = ""
)
