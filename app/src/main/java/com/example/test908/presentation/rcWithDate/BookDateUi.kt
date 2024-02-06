package com.example.test908.presentation.rcWithDate

import com.example.test908.presentation.common.BaseItem

data class BookDateUi(
    override val itemId: String,
    val date: String
) : BaseItem
