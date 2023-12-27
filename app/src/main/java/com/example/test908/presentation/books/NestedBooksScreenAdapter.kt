package com.example.test908.presentation.books

import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class NestedBooksScreenAdapter(onNestedClick: (String) -> Unit) : AsyncListDifferDelegationAdapter<BaseItem>(
    diffUtils()
) {
    init {
       delegatesManager
            .addDelegate(
              NESTED_ITEM_VIEW_TYPE,
               nestedItemAdapterDelegate(onClickNested = onNestedClick)
           )
    }
    companion object {
        const val NESTED_ITEM_VIEW_TYPE = -1000
    }
}


private fun diffUtils() = itemCallback<BaseItem>(
    areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
    areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
    getChangePayload = { _, _ ->
        Any()
    }
)
