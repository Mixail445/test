package com.example.test908.utils

import com.example.test908.presentation.reviews.FavoriteData

interface FavoriteLocalSourceInt {
    fun getId(): FavoriteData?
    fun setId(favoriteData: FavoriteData)
    fun clearId()
}
