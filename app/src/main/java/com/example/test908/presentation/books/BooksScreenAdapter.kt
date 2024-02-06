package com.example.test908.presentation.books

import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class BooksScreenAdapter(
    onNestedClicked: (String) -> Unit,
    onClickBannerBottom: (String) -> Unit,
    onClickBlackBanner: (String) -> Unit,
    onClickBannerGreen: (String) -> Unit
) : AsyncListDifferDelegationAdapter<BaseItem>(
    diffUtils()
) {
    init {
        delegatesManager
            .addDelegate(
                BOOKS_ITEM_VIEW_TYPE,
                booksItemAdapterDelegate(onNestedClicked)
            )
            .addDelegate(
                LIMITED_SERIES_COMMERCIAL_BANNER_GREEN_ITEM_VIEW_TYPE,
                bannerItemAdapterDelegateGreen(onClickBannerGreen)
            )
            .addDelegate(
                LIMITED_SERIES_COMMERCIAL_BANNER_ITEM_VIEW_TYPE,
                bannerItemAdapterDelegate(onClickBannerBottom)
            )
            .addDelegate(
                LIMITED_SERIES_COMPLETED_REGISTRATION_BANNER_VIEW_TYPE,
                blackBannerItemAdapterDelegate(onClickBlackBanner)
            )
    }
    companion object {
        const val BOOKS_ITEM_VIEW_TYPE = -1001
        const val LIMITED_SERIES_COMMERCIAL_BANNER_ITEM_VIEW_TYPE = -1002
        const val LIMITED_SERIES_COMMERCIAL_BANNER_GREEN_ITEM_VIEW_TYPE = -1003
        const val LIMITED_SERIES_COMPLETED_REGISTRATION_BANNER_VIEW_TYPE = -1004
    }
}

private fun diffUtils() = itemCallback<BaseItem>(
    areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
    areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
    getChangePayload = { _, _ ->
        Any()
    }
)
