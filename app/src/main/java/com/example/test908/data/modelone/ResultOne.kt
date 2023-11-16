package com.example.test908.data.modelone

data class ResultOne(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<Result>,
    val section: String,
    val status: String,
)