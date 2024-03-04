package com.example.todoslist.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoslist.repository.TodoRepository

class TodosViewModelProvider(private val todoRepository: TodoRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)){
            return TodoViewModel(todoRepository) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }
}