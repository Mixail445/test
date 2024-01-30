package com.example.test908.presentation.books

import com.example.test908.databinding.ItemHorizontalBooksBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun nestedItemAdapterDelegate(onClickNested: (String) -> Unit): AdapterDelegate<List<BaseItem>> {
    return adapterDelegateViewBinding<LinkUi, BaseItem, ItemHorizontalBooksBinding>({ layoutInflater, parent ->
        ItemHorizontalBooksBinding.inflate(layoutInflater, parent, false)
    }) {
        binding.buttonShop.setOnClickListener {
            onClickNested(item.url)
        }
        bind {
            binding.buttonShop.text = item.name
        }
    }
}
