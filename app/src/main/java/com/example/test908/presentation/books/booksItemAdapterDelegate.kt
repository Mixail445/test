package com.example.test908.presentation.books

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.test908.R
import com.example.test908.databinding.ItemBooksBinding
import com.example.test908.presentation.common.BaseItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun booksItemAdapterDelegate(onNestedClick: (String) -> Unit) =
    adapterDelegateViewBinding<BooksUi, BaseItem, ItemBooksBinding>(
        viewBinding = { layoutInflater, parent ->
            ItemBooksBinding.inflate(layoutInflater, parent, false)
        }
    ) {
        val nestedBooksScreenAdapter = NestedBooksScreenAdapter(onNestedClick)
        binding.rcBook.layoutManager = LinearLayoutManager(
            itemView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rcBook.adapter = nestedBooksScreenAdapter
        bind {
            with(item) {
                    nestedBooksScreenAdapter.items = link
                    binding.tvBookDetail.text = booksDetail
                    binding.tvNameBook.text = nameBooksStore
                    binding.tvNameAuthor.text = nameAuthor
                    Glide.with(itemView.context).load(photoBook)
                        .error(R.drawable.img)
                        .placeholder(R.drawable.img)
                        .into(binding.ivBook)
                }
            }
        }
