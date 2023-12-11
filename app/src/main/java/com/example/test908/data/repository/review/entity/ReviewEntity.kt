package com.example.test908.data.repository.review.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class ReviewEntity(
    var byline: String = "",
    var multimedia: String = "",
    var publishedDate: String = "",
    var shortUrl: String = "",
    var title: String = "",
    var abstract: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
