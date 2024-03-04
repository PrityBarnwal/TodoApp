package com.example.todoslist.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoslist.model.TodoData
import com.example.todoslist.model.TodoDataResponse
import com.example.todoslist.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _todoData = MutableLiveData<TodoData>()
    val todoData: LiveData<TodoData> get() = _todoData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    suspend fun getTodos() {
        _isLoading.value = true // Set loading to true before making the network call
        try {
            _todoData.value = repository.getTodos()
        } catch (e: Exception) {
            // Handle the exception, e.g., log it or show an error message
        } finally {
            _isLoading.value = false // Set loading to false when the network call is complete
        }
    }

    /*suspend fun getTodos() {
        viewModelScope.launch {
            Log.d("TAG", "getTodos: ${repository.getTodos()}")
            _todoData.value = repository.getTodos()
        }
    }*/
}