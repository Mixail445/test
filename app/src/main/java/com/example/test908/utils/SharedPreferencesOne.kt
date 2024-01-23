package com.example.test908.utils

interface SharedPreferencesOne<T> {
    fun getData(key: String): T?
    fun clearData()
    fun setData(key: String, data: T)
}

