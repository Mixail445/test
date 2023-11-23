package com.example.test908.data.dao

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
    val status: String
)

fun NumResultDto.mapToEntity(): NumResult =
    NumResult(
        copyright = copyright,
        lastUpdated = lastUpdated,
        numResults = numResults,
        results = results.map { it.mapToEntity() },
        section = section,
        status = status
    )
