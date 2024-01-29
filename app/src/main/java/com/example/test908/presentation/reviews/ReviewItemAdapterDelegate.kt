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
        with(binding) {
            photoItem.setOnClickListener {
                onItemClicked(item.itemId)
            }
            imageView.setOnClickListener {
                onFavoriteClicked(item.itemId)
            }
        }
        bind {
            binding.run {
                with(item) {
                    bodyItem.text = abstract
                    titleItem.text = title
                    dataItem.text = date
                    nameItem.text = byline
                    Glide.with(itemView.context).load(pictureSrc)
                        .error(R.drawable.img)
                        .placeholder(R.drawable.img)
                        .into(binding.photoItem)
                    imageView.setImageDrawable(favorite)
                }
            }
        }
    }
