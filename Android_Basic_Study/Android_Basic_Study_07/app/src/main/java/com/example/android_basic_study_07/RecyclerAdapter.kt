package com.example.android_basic_study_07

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_07.databinding.RecyclerItemBinding
import com.example.android_basic_study_07.model.Todo
import com.example.android_basic_study_07.viewModel.MainViewModel

class RecyclerAdapter(private val viewModel: MainViewModel): ListAdapter<Todo, RecyclerAdapter.MyViewHolder>(
    diffUtil
) {
    inner class MyViewHolder(private val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(todo : Todo) {
            binding.model = todo
            binding.btnRemove.setOnClickListener {
                viewModel.removeTodo(todo.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
    }
}