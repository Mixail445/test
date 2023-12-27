package com.example.test908.presentation.reviews

import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ReviewsScreenAdapter(
    val onItemClicked: (String) -> Unit,
    val onFavoriteClick: (String) -> Unit
) : AsyncListDifferDelegationAdapter<BaseItem>(diffUtils()) {
    init {
        delegatesManager
            .addDelegate(
                REVIEW_ITEM_VIEW_TYPE,
                reviewItemAdapterDelegate(onItemClicked, onFavoriteClick)
            )
    }
    companion object {
        const val REVIEW_ITEM_VIEW_TYPE = -1001
    }

}


private fun diffUtils() = itemCallback<BaseItem>(
    areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
    areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
    getChangePayload = { _, _ ->
        Any()
    }
)
