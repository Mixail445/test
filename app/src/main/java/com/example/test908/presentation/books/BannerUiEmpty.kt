package com.example.test908.presentation.books

import com.example.test908.presentation.common.BaseItem

data class BannerUiEmpty(
    override val itemId: String,
    val textBottom: String,
    val titleText: String,
    val bodyText: String,
) : BaseItem
