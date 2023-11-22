package com.example.test908.data.dao

import com.example.test908.domain.model.Multimedia

data class MultimediaDto(
    val caption: String,
    val copyright: String,
    val format: String,
    val height: Int,
    val subtype: String,
    val type: String,
    val url: String,
    val width: Int,
)

fun MultimediaDto.mapToEntity(): Multimedia =
    Multimedia(
        caption,
        copyright,
        format,
        height,
        subtype,
        type,
        url,
        width
    )