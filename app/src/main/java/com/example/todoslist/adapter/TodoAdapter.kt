package com.example.todoslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoslist.databinding.ItemListTodoBinding
import com.example.todoslist.model.TodoDataResponse

class TodoAdapter(private val data: List<TodoDataResponse>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemListTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoDataResponse: TodoDataResponse) {
            binding.apply {
                itemTodos.text = todoDataResponse.todo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoItem = data[position]
        holder.bind(todoItem)
    }
}