package com.example.lifebalance.screens.Todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifebalance.data.Todo
import com.example.lifebalance.repositories.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TodoViewModel:ViewModel(),KoinComponent {

    private val repository:TodoRepository by inject()

    private val _todos:MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
    val todo = _todos.asStateFlow()

    init {
        getTodo()
    }

    private fun getTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTodo().collect{ data ->
                _todos.update{data}
            }
        }
    }
    fun addTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }
    fun updateTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }
    fun deleteTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }
}