package com.example.todoslist.model

data class TodoDataResponse(
    val id:Int,
    val todo:String,
    val completed:Boolean,
    val userId:Int,
)

data class TodoData(val todos:List<TodoDataResponse>)