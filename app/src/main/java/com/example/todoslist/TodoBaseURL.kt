package com.example.todoslist

import com.example.todoslist.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TodoBaseURL {
    private const val BASE_URL = "https://dummyjson.com/"
    fun createTodo(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}