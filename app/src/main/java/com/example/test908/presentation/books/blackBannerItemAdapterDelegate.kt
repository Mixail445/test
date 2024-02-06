package com.example.test908.presentation.books

import com.example.test908.databinding.DialogFragmentLimitedTwoBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun blackBannerItemAdapterDelegate(onClick: (String) -> Unit) =
    adapterDelegateViewBinding<BlackBannerUi, BaseItem, DialogFragmentLimitedTwoBinding>(
        viewBinding = { layoutInflater, parent ->
            DialogFragmentLimitedTwoBinding.inflate(layoutInflater, parent, false)
        }
) {
    binding.bvLimited.setOnClickListener {
        onClick(item.itemId)
    }
}
