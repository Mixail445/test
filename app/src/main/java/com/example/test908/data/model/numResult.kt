package com.example.test908.data.model

import com.example.test908.domain.entity.NumResultEntity
import com.google.gson.annotations.SerializedName

data class NumResult(
    val copyright: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("num_results")
    val numResults: Int,
    val results: List<Story>,
    val section: String,
    val status: String,
)

fun NumResult.mapIt(): NumResultEntity =
    NumResultEntity(
        copyright = copyright,
        lastUpdated = lastUpdated,
        numResults = numResults,
        results = results.map { it.mapIt() },
        section = section,
        status = status
    )