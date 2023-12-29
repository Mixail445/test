package com.example.test908.presentation.reviews

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.test908.presentation.common.BaseItem
import com.example.test908.utils.itemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ReviewsScreenAdapter(
   val onItemClicked: (String) -> Unit
) : AsyncListDifferDelegationAdapter<BaseItem>(diffUtils()) {
    init {
        delegatesManager
            .addDelegate(
                REVIEW_ITEM_VIEW_TYPE,
                reviewItemAdapterDelegate()
            )

    }
    companion object {
        const val REVIEW_ITEM_VIEW_TYPE = -1001
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any?>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.takeIf { payloads.isEmpty() }?.applyData(position)
    }
    private fun ViewHolder.applyData(position: Int) {
        itemView.setOnClickListener { onItemClicked(position.toString()) }
    }

}

private fun diffUtils() = itemCallback<BaseItem>(
    areItemsTheSame = { oldItem, newItem -> oldItem.itemId == newItem.itemId },
    areContentsTheSame = { oldItem, newItem -> oldItem == newItem },
    getChangePayload = { _, _ ->
        Any()
    }
)
