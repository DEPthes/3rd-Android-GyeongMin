package com.example.android_basic_study_07

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_basic_study_07.model.Todo
import com.example.android_basic_study_07.viewModel.MainViewModel

object MyBindingAdapter {
    @BindingAdapter("items", "viewModel")
    @JvmStatic
    fun setItem(recyclerView: RecyclerView, todoList: List<Todo>?, viewModel: MainViewModel) {
        if(recyclerView.adapter == null) {
            val adapter = RecyclerAdapter(viewModel)
            recyclerView.adapter = adapter
        }

        todoList?.let {
            val myAdapter = recyclerView.adapter as RecyclerAdapter
            myAdapter.submitList(it)
            myAdapter.notifyDataSetChanged()
        }
    }

}