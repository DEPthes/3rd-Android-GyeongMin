package com.example.android_basic_study_07.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_basic_study_07.model.Todo

class MainViewModel: ViewModel() {
    private var _todoList = MutableLiveData<List<Todo>>()
    val todoList : LiveData<List<Todo>>
        get() = _todoList

    private var items = mutableListOf<Todo>()
    init {
        items = arrayListOf(
            Todo("테스트1", 1),
            Todo("테스트2", 2)
        )
        _todoList.postValue(items)
    }

    fun addTodo(content: String) {
        if(content == "") {
            return
        }
        val todo = Todo(content, todoList.value!!.size)
        items.add(todo)
        _todoList.postValue(items)
    }

    fun removeTodo(id: Int) {
        items.removeAt(id)
        _todoList.postValue(items)
    }
}