package com.example.test908.domain.entity

data class NumResultEntity(
    val copyright: String,
    val lastUpdated: String,
    val numResults: Int,
    val results: List<StoryEntity>,
    val section: String,
    val status: String,
)
