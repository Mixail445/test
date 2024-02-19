package com.example.test908.utils

import com.example.test908.presentation.reviews.FavoriteData
import javax.inject.Inject

class FavoriteLocalSourceImpl
    @Inject
    constructor(
        private val sharedPreferencesOne: SharedPreferencesOne<FavoriteData>,
    ) : FavoriteLocalSourceInt {
        override fun getId(): FavoriteData? = sharedPreferencesOne.getData("key")

        override fun setId(favoriteData: FavoriteData) {
            sharedPreferencesOne.setData("key", favoriteData)
        }

        override fun clearId() {
            sharedPreferencesOne.clearData()
        }
    }
