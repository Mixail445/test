package com.example.test908.presentation.reviews

import com.bumptech.glide.Glide
import com.example.test908.R
import com.example.test908.databinding.ItemReviewBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun reviewItemAdapterDelegate(
    onItemClicked: (String) -> Unit,
    onFavoriteClicked: (String) -> Unit,
) = adapterDelegateViewBinding<ReviewUi, BaseItem, ItemReviewBinding>(
    viewBinding = { layoutInflater, parent ->
        ItemReviewBinding.inflate(layoutInflater, parent, false)
    },
) {
    with(binding) {
        ivReview
        ivReview.setOnClickListener {
            onItemClicked(item.itemId)
        }
        ivFollow.setOnClickListener {
            onFavoriteClicked(item.itemId)
        }
    }
    bind {
        binding.run {
            with(item) {
                tvBody.text = abstract
                tvTitle.text = title
                tvDate.text = date
                tvAuthor.text = byline
                Glide.with(itemView.context).load(pictureSrc)
                    .error(R.drawable.img)
                    .placeholder(R.drawable.img)
                    .into(binding.ivReview)
                ivFollow.setImageDrawable(favorite)
            }
        }
    }
}
