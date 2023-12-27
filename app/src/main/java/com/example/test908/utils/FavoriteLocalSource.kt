package com.example.test908.utils

import com.example.test908.presentation.reviews.FavoriteData
import javax.inject.Inject

class FavoriteLocalSource @Inject constructor(
    private val sharedPreferencesOne: SharedPreferencesOne<FavoriteData>,
                                              private val favoriteData: FavoriteData
) {
    fun getId(key: String) {
        sharedPreferencesOne.getData(key)
    }
    fun setId(key: String) {
        sharedPreferencesOne.setData(key, favoriteData)
    }
    fun clearId() {
        sharedPreferencesOne.clearData()
    }
}
