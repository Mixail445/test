package com.example.test908.presentation.rcWithDate

import com.example.test908.databinding.ItemRcWithMainBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun rcWithDateAdapterDelegate() =
    adapterDelegateViewBinding<BookOfferUi, BaseItem, ItemRcWithMainBinding>(
        viewBinding =
        { layoutInflater, parent ->
            ItemRcWithMainBinding.inflate(layoutInflater, parent, false)
        }

    ) {
        bind {
            binding.titleMain.text = item.title
            binding.DescriptionMain.text = item.description
            binding.buyMain.text = item.price
        }
    }
