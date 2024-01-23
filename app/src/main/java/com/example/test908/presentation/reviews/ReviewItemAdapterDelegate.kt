package com.example.test908.presentation.reviews

import com.bumptech.glide.Glide
import com.example.test908.R
import com.example.test908.databinding.ItemReviewBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun reviewItemAdapterDelegate(
    onItemClicked: (String) -> Unit,
    onFavoriteClicked: (String) -> Unit
) =
    adapterDelegateViewBinding<ReviewUi, BaseItem, ItemReviewBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemReviewBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        binding.photoItem.setOnClickListener {
            onItemClicked(item.itemId)
        }
        binding.imageView.setOnClickListener {
            onFavoriteClicked(item.itemId)
        }

        bind {
            binding.run {
                bodyItem.text = item.abstract
                titleItem.text = item.title
                dataItem.text = item.date
                nameItem.text = item.byline
                Glide.with(itemView.context).load(item.pictureSrc)
                    .error(R.drawable.img)
                    .placeholder(R.drawable.img)
                    .into(binding.photoItem)
                imageView.setImageDrawable(item.favorite)
            }
        }
    }
