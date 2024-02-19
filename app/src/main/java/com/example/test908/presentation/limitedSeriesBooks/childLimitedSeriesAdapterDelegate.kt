package com.example.test908.presentation.limitedSeriesBooks

import com.example.test908.databinding.ItemLimitedSeriesDateBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun childLimitedSeriesAdapterDelegate() =
    adapterDelegateViewBinding<BookDateUi, BaseItem, ItemLimitedSeriesDateBinding>(
        viewBinding =
            { layoutInflater, parent ->
                ItemLimitedSeriesDateBinding.inflate(layoutInflater, parent, false)
            },
    ) {
        bind {
            binding.textView.text = item.date
        }
    }
