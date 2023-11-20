package com.example.test908.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test908.R
import com.example.test908.data.model.Story

class ReviewsAdapter : ListAdapter<Story, ReviewsAdapter.Holder>(ReviewsComparator), Filterable {
    private var list = kotlin.collections.ArrayList(currentList)
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
        holder.name.text = itemsViewModel.published_date
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

    object ReviewsComparator : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.byline == newItem.byline
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val list1 = mutableListOf<Story>()
                val filterSeq = p0.toString().lowercase()
                if (filterSeq.isNotEmpty()) {
                    list.forEach {
                        if (it.title.lowercase().contains(filterSeq) ||
                            it.abstract.lowercase().contains(filterSeq) ||
                            it.byline.lowercase().contains(filterSeq) ||
                            it.published_date.lowercase().contains(filterSeq)
                        ) {
                            list1.add(it)
                        }
                    }
                } else {
                    list1.addAll(list)
                }
                val result = FilterResults()
                result.values = list1
                return result
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                submitList(p1?.values as? List<Story>)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Story)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setData(list: List<Story>) {
        this.list = (list as ArrayList<Story>?)!!
        submitList(list)
    }
}

