package com.example.test908.domain.model

import com.example.test908.presentation.reviewList.MultimediaUi

data class Multimedia(
    val caption: String,
    val copyright: String,
    val format: String,
    val height: Int,
    val subtype: String,
    val type: String,
    val url: String,
    val width: Int,
)
fun Multimedia.mapFromEntity(): MultimediaUi =
    MultimediaUi(
        url = url
    )