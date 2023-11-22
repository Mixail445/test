package com.example.test908.data.model

import com.example.test908.domain.entity.MultimediaEntity

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

fun Multimedia.mapIt(): MultimediaEntity =
    MultimediaEntity(
        caption,
        copyright,
        format,
        height,
        subtype,
        type,
        url,
        width
    )