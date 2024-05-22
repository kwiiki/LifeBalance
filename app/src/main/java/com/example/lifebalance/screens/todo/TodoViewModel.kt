package com.example.lifebalance.screens.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifebalance.data.todo.Todo
import com.example.lifebalance.repositories.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TodoViewModel : ViewModel(), KoinComponent {

    private val repository: TodoRepository by inject()

    private val _todosByDate = MutableStateFlow<Map<String, List<Todo>>>(emptyMap())
    val todosByDate: StateFlow<Map<String, List<Todo>>> = _todosByDate

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getTodo()
    }
    fun getSortedDates(): List<String> {
        return repository.getSortedDates(todosByDate.value)
    }

    private fun getTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            repository.getTodosByDate().collect { data ->
                _todosByDate.value = data
                _isLoading.value = false
            }
        }
    }
    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }
    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }
    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }
}