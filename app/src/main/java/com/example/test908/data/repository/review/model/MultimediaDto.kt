package com.example.test908.data.repository.review.model

import com.example.test908.domain.repository.review.model.Multimedia

data class MultimediaDto(
    val url: String,
)

fun MultimediaDto.mapToDomain(): Multimedia = Multimedia(
    url,
)
