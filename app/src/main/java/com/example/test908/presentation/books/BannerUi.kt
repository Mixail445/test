package com.example.test908.presentation.books

import com.example.test908.presentation.common.BaseItem

data class BannerUi(
    override val itemId: String,
    val title: String,
    val body: String,
    val textBottom: String,
    val pbVisible: Boolean,
    val cBVisible: Boolean,
    val bvVisible: Boolean
) : BaseItem
