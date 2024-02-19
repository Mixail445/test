package com.example.test908.presentation.books

import androidx.core.view.isVisible
import com.example.test908.databinding.DialogFragmentLimitedFourBinding
import com.example.test908.databinding.DialogFragmentLimitedSeriesBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun bannerItemAdapterDelegate(onClick: (String) -> Unit) =
    adapterDelegateViewBinding<BannerUi, BaseItem, DialogFragmentLimitedSeriesBinding>(
        viewBinding = { layoutInflater, parent ->
            DialogFragmentLimitedSeriesBinding.inflate(layoutInflater, parent, false)
        },
    ) {
        binding.bvLimited.setOnClickListener {
            onClick(item.itemId)
        }
        bind {
            with(item) {
                binding.tvBodyLimited.text = body
                binding.bvLimited.text = textBottom
                binding.tvTitleLimited.text = title
                binding.progressBar.isVisible = pbVisible
                binding.bvLimited.isVisible = bvVisible
            }
        }
    }

fun bannerItemAdapterDelegateGreen(onClickGreen: (String) -> Unit) =
    adapterDelegateViewBinding<BannerUiEmpty, BaseItem, DialogFragmentLimitedFourBinding>(
        viewBinding = { layoutInflater, parent ->
            DialogFragmentLimitedFourBinding.inflate(layoutInflater, parent, false)
        },
    ) {
        binding.bvLimited.setOnClickListener {
            onClickGreen(item.itemId)
        }
        bind {
            binding.bvLimited.text = item.textBottom
            binding.tvTitleLimited.text = item.titleText
            binding.tvBodyLimited.text = item.bodyText
        }
    }
