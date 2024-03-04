package com.example.todoslist.repository

import com.example.todoslist.model.TodoData
import com.example.todoslist.model.TodoDataResponse
import com.example.todoslist.network.ApiService

class TodoRepository (private val apiService:ApiService){
    suspend fun getTodos():TodoData{
        return apiService.getTodos()
    }
}