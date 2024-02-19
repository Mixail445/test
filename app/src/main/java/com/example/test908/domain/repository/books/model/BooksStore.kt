package com.example.test908.domain.repository.books.model

import com.example.test908.presentation.books.LinkUi

data class BooksStore(
    val name: String,
    val url: String
){
    fun map()=LinkUi(
        url = url,
        name = name,
        itemId = ""
    )
}
