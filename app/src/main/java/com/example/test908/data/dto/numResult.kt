package com.example.test908.data.dto

import com.example.test908.domain.model.NumResult
import com.google.gson.annotations.SerializedName

data class NumResultDto(
    val copyright: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("num_results")
    val numResults: Int,
    val results: List<StoryDto>,
    val section: String,
    val status: String,
)

fun NumResultDto.mapToDomain(): NumResult =
    NumResult(
        copyright = copyright,
        lastUpdated = lastUpdated,
        numResults = numResults,
        results = results.map { it.mapToDomain() },
        section = section,
        status = status
    )