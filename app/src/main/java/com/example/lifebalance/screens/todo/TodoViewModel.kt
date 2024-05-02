package com.example.lifebalance.screens.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifebalance.data.Todo
import com.example.lifebalance.data.addDate
import com.example.lifebalance.repositories.TodoRepository
import com.example.lifebalance.repositories.getTodayDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.SortedMap

class TodoViewModel:ViewModel(),KoinComponent {

    private val repository:TodoRepository by inject()

    private val _todosByDate: MutableStateFlow<HashMap<String, List<Todo>>> = MutableStateFlow(hashMapOf())
    val todosByDate = _todosByDate.asStateFlow()


    init {
        getTodo()
    }

    fun getSortedDates(): List<String> {
        val todayDate = getTodayDate()
        val dates = todosByDate.value.keys.toList()

        val sortedDates = dates.filter { it >= todayDate }
            .sortedWith(
                compareBy<String> { date ->
                    when {
                        date == todayDate -> 0
                        else -> 1
                    }
                }.thenByDescending { it }
            )

        return sortedDates
    }

    private fun getTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTodosByDate().collect  { data ->
                _todosByDate.update { data }
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