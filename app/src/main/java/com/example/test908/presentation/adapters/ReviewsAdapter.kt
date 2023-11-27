package com.example.test908.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test908.R
import com.example.test908.presentation.reviewList.StoryUi

class ReviewsAdapter :
    ListAdapter<StoryUi, ReviewsAdapter.Holder>(ReviewsComparator) {
    private var onClickListener: OnClickListener? = null

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = itemView.findViewById(R.id.title_item)
        val body: TextView = itemView.findViewById(R.id.body_item)
        val data: TextView = itemView.findViewById(R.id.data_item)
        val name: TextView = itemView.findViewById(R.id.name_item)
        val photo: ImageView = itemView.findViewById(R.id.photo_item)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemsViewModel = getItem(position)
        Glide.with(holder.itemView.context)
            .load(itemsViewModel?.multimedia?.get(0)?.url)
            .error(R.drawable.img)
            .placeholder(R.drawable.img)
            .into(holder.photo)
        holder.title.text = itemsViewModel.title
        holder.body.text = itemsViewModel.abstract
        holder.data.text = itemsViewModel.byline
        holder.name.text = itemsViewModel.publishedDate
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, itemsViewModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_reviews, parent, false)
        return Holder(itemView)
    }

    object ReviewsComparator : DiffUtil.ItemCallback<StoryUi>() {
        override fun areItemsTheSame(oldItem: StoryUi, newItem: StoryUi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: StoryUi, newItem: StoryUi): Boolean {
            return oldItem.byline == newItem.byline
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: StoryUi)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}
