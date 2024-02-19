package com.example.test908.presentation.books

import com.example.test908.presentation.common.BaseItem

data class LinkUi(
    val name: String,
    val url: String,
    override val itemId: String
) : BaseItem
