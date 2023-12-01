package com.example.test908.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test908.R
import com.example.test908.databinding.ItemReviewsBinding
import com.example.test908.presentation.reviewList.ReviewUi

class ReviewAdapterDelegate : DelegateAdapter<ReviewUi, ReviewAdapterDelegate.ReviewViewHolder>(
    ReviewUi::class.java
) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun bindViewHolder(
        model: ReviewUi,
        viewHolder: ReviewViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>
    ) {
        viewHolder.bind(model)
    }

    inner class ReviewViewHolder(private val binding: ItemReviewsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bind(item: ReviewUi) {
            with(binding) {
                bodyItem.text = item.abstract
                titleItem.text = item.title
                dataItem.text = item.publishedDate
                nameItem.text = item.publishedDate
                Glide.with(itemView.context).load(item.multimedia[1].url)
                    .error(R.drawable.img)
                    .placeholder(R.drawable.img)
                    .into(binding.photoItem)
            }
        }
    }
}
