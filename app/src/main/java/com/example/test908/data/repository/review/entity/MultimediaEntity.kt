package com.example.test908.data.repository.review.entity

import androidx.room.PrimaryKey

data class MultimediaEntity(
    val url: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
