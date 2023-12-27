package com.example.test908.presentation.books

import android.os.Parcelable
import com.example.test908.presentation.common.BaseItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class BooksUi(
    override val itemId: String,
    val photoBook: String,
    val nameBooksStore: String,
    val nameAuthor: String,
    val booksDetail: String,
    val link: @RawValue List<LinkUi>
) : Parcelable, BaseItem
