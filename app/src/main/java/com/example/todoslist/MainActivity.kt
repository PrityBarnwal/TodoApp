package com.example.todoslist

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todoslist.adapter.TodoAdapter
import com.example.todoslist.databinding.ActivityMainBinding
import com.example.todoslist.model.TodoDataResponse
import com.example.todoslist.network.ApiService
import com.example.todoslist.repository.TodoRepository
import com.example.todoslist.viewModel.TodoViewModel
import com.example.todoslist.viewModel.TodosViewModelProvider
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(
            this,
            TodosViewModelProvider(TodoRepository(TodoBaseURL.createTodo()))
        )[TodoViewModel::class.java]

        binding.isInternet = internetCnnection()

        if (internetCnnection()) {
            lifecycleScope.launch {
                todoViewModel.getTodos()
            }

            todoViewModel.isLoading.observe(this) {
                binding.isProgress.visibility = if (it) View.VISIBLE else View.GONE
            }
            todoViewModel.todoData.observe(this) {
                todoAdapter = TodoAdapter(it.todos)
                binding.recTodos.adapter = todoAdapter
            }

            filterBySpinner()

            binding.tvSearch.doAfterTextChanged {
                updateSpinner(binding.spinner.selectedItem.toString(), it.toString())
            }
        }
    }


    private fun updateSpinner(filter: String, search: String) {
        val filterTodoItem = todoViewModel.todoData.value?.todos?.filter {
            val filterSearch = when (filter) {
                "All" -> true
                "Complete" -> it.completed
                "InComplete" -> !it.completed
                else -> false
            }
            val searchItem = it.todo.contains(search, ignoreCase = true)
            filterSearch && searchItem

        }
        todoAdapter = TodoAdapter(filterTodoItem ?: emptyList())
        binding.recTodos.adapter = todoAdapter
    }

    private fun internetCnnection(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    private fun filterBySpinner() {
        val filterAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.filter)
        )
        filterAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.spinner.adapter = filterAdapter
        binding.apply {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    updateSpinner(selectedItem, tvSearch.text.toString())
                } // to close the onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {
                    //doNothing
                }
            }
        }
    }
}
