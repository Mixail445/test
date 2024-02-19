package com.example.test908.presentation.limitedSeriesBooks

import com.example.test908.databinding.ItemLimitedSeriesMainBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun limitedSeriesAdapterDelegate() =
    adapterDelegateViewBinding<BookOfferUi, BaseItem, ItemLimitedSeriesMainBinding>(
        viewBinding =
            { layoutInflater, parent ->
                ItemLimitedSeriesMainBinding.inflate(layoutInflater, parent, false)
            },
    ) {
        bind {
            binding.titleMain.text = item.title
            binding.DescriptionMain.text = item.description
            binding.buyMain.text = item.price
        }
    }
