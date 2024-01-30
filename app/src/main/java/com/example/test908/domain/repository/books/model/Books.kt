package com.example.test908.domain.repository.books.model

import com.example.test908.presentation.books.BooksUi

data class Books(
    val description: String,
    val title: String,
    val author: String,
    val bookImage: String,
    val buyLinksName: List<BooksStore>,
    val id: Long
) {
    fun mapToUi() = BooksUi(
     photoBook = bookImage,
        booksDetail = description,
        nameBooksStore = title,
        itemId = id.toString(),
        nameAuthor = author,
        link = buyLinksName.map { it.map() }
    )
}
