package com.example.test908.presentation.rcWithDate

import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class RcWithDateAdapter : AsyncListDifferDelegationAdapter<BaseItem>(
    diffUtils()
) {
    init {
        delegatesManager
            .addDelegate(
                CHILD_DATE_ITEM_VIEW_TYPE,
                childRcWithDateAdapterDelegate()
            )
            .addDelegate(
                DATE_ITEM_VIEW_TYPE,
                rcWithDateAdapterDelegate()
            )
    }

    companion object {
        const val CHILD_DATE_ITEM_VIEW_TYPE = -1001
        const val DATE_ITEM_VIEW_TYPE = -1002
    }
}
private fun diffUtils() = itemCallback<BaseItem>(
    areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
    areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
    getChangePayload = { _, _ ->
        Any()
    }
)
