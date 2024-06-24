package com.example.android_basic_study_08

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_basic_study_08.databinding.ItemBookmarkBinding

class BookmarkAdapter() : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    val items = mutableListOf<String>("1", "2", "3")
    inner class ViewHolder(private val binding: ItemBookmarkBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(items: String) {
            Glide.with(binding.imageBookmark)
                .load("https://i.namu.wiki/i/eFwZuvkmTOGdMQjTDUsyl8HqgCrZlQzVm2XWIubFhTbJ4t7YOaLCT4mwepCi0WwJsrX6uZ2b1Gi8DHqnIEvnDab4x35GZKyV1A3T2mNMx2QV5gYmcto2DZKiy7hU0b2drjvtPm2-qstm291hLGEw2Q.webp")
                .into(binding.imageBookmark)
        }
    }
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var itemClickListener: onItemClickListener

    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookmarkAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }

}