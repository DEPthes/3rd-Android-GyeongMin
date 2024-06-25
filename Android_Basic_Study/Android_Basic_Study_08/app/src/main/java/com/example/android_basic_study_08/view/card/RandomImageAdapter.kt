package com.example.android_basic_study_08.view.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.android_basic_study_08.data.local.BookmarkImage
import com.example.android_basic_study_08.databinding.ItemCardBinding
import com.example.android_basic_study_08.databinding.ItemNewimageBinding
import com.example.android_basic_study_08.entity.NewImage
import com.example.android_basic_study_08.entity.RandomImage
import com.example.android_basic_study_08.view.home.NewImageAdapter

class RandomImageAdapter: RecyclerView.Adapter<RandomImageAdapter.ViewHolder>() {
    val items = mutableListOf<RandomImage>()
    inner class ViewHolder(private val binding: ItemCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(items: RandomImage) {
            Glide.with(binding.ivRandomPhoto)
                .load(items.urls)
                .into(binding.ivRandomPhoto)
            binding.ivBookmark.setOnClickListener {
                itemClickListener.onItemClick(bookmarkImage = BookmarkImage(items.id, items.urls), adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RandomImageAdapter.ViewHolder {
        return ViewHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RandomImageAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(newList: List<RandomImage>) {
        items.addAll(newList)
        notifyItemRangeChanged(itemCount, newList.size)
    }

    interface OnItemClickListener {
        fun onItemClick(bookmarkImage: BookmarkImage, position: Int)
    }

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}