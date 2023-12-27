package com.example.test908.presentation.books

import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class BooksScreenAdapter(onNestedClicked: (String) -> Unit) : AsyncListDifferDelegationAdapter<BaseItem>(
    diffUtils()
) {
    init {
        delegatesManager
            .addDelegate(
                BOOKS_ITEM_VIEW_TYPE,
                booksItemAdapterDelegate(onNestedClicked)
            )
    }
    companion object {
        const val BOOKS_ITEM_VIEW_TYPE = -1002
    }
}

private fun diffUtils() = itemCallback<BaseItem>(
    areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
    areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
    getChangePayload = { _, _ ->
        Any()
    }
)
