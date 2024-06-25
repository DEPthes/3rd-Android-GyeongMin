package com.example.android_basic_study_08.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.android_basic_study_08.data.local.BookmarkImage
import com.example.android_basic_study_08.databinding.ItemBookmarkBinding

class BookmarkAdapter() : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    var items = mutableListOf<BookmarkImage>()
    inner class ViewHolder(private val binding: ItemBookmarkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(items: BookmarkImage) {
            Glide.with(binding.imageBookmark)
                .load(items.urls)
                .transform(RoundedCorners(40))
                .into(binding.imageBookmark)
            itemView.setOnClickListener {
                itemClickListener.onItemClick(items.id)
            }
        }
    }
    interface onItemClickListener {
        fun onItemClick(id: String)
    }

    private lateinit var itemClickListener: onItemClickListener

    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun setData(bookmarkList: List<BookmarkImage>) {
        items = bookmarkList.toMutableList()
        notifyDataSetChanged()
    }
}