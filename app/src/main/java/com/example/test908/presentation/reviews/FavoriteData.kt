package com.example.test908.presentation.reviews

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
@JsonClass(generateAdapter = true)
@Parcelize
data class FavoriteData(@Json(name = "list") val item: Set<String>) : Parcelable
