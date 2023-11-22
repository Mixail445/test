package com.example.test908.domain.model

import com.example.test908.presentation.reviewList.NumResultUi

data class NumResult(
    val copyright: String,
    val lastUpdated: String,
    val numResults: Int,
    val results: List<Story>,
    val section: String,
    val status: String,
)
fun NumResult.mapFromEntity(): NumResultUi =
    NumResultUi(
        copyright = copyright,
        lastUpdated = lastUpdated,
        numResults = numResults,
        results = results.map { it.mapFromEntity() },
        section = section,
        status = status
    )
