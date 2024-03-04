package com.example.todoslist.network

import com.example.todoslist.model.TodoData
import com.example.todoslist.model.TodoDataResponse
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): TodoData
}