package com.example.test908.presentation.rcWithDate

import com.example.test908.databinding.ItemRcWithDateBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun childRcWithDateAdapterDelegate() =
    adapterDelegateViewBinding<BookDateUi, BaseItem, ItemRcWithDateBinding>(
        viewBinding =
{ layoutInflater, parent ->
    ItemRcWithDateBinding.inflate(layoutInflater, parent, false)
}
) {
    bind {
        binding.textView.text = item.date
    }
}
