package com.example.test908.data.model

data class Result(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<Story>,
    val section: String,
    val status: String,
)