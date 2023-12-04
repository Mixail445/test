package com.example.test908.domain.model

import com.example.test908.presentation.reviewList.NumResultUi

data class NumResult(
    val copyright: String,
    val lastUpdated: String,
    val numResults: Int,
    val results: List<Review>,
    val section: String,
    val status: String
)
fun NumResult.mapToUi(): NumResultUi =
    NumResultUi(
        copyright = copyright,
        lastUpdated = lastUpdated,
        numResults = numResults,
        results = results.map { it.mapToUi() },
        section = section,
        status = status
    )
