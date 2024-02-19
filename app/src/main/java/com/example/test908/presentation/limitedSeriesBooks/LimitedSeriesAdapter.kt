package com.example.test908.presentation.limitedSeriesBooks

import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class LimitedSeriesAdapter : AsyncListDifferDelegationAdapter<BaseItem>(
    diffUtils(),
) {
    init {
        delegatesManager.addDelegate(
            CHILD_DATE_ITEM_VIEW_TYPE,
            childLimitedSeriesAdapterDelegate(),
        ).addDelegate(
            DATE_ITEM_VIEW_TYPE,
            limitedSeriesAdapterDelegate(),
        )
    }

    companion object {
        const val CHILD_DATE_ITEM_VIEW_TYPE = -1001
        const val DATE_ITEM_VIEW_TYPE = -1002
    }
}

private fun diffUtils() =
    itemCallback<BaseItem>(
        areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
        getChangePayload = { _, _ ->
            Any()
        },
    )
