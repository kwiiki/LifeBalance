package com.example.lifebalance.repositories

import com.example.lifebalance.data.todo.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun getTodo(): Flow<List<Todo>>
    suspend fun getTodosByDate(): Flow<Map<String, List<Todo>>>
    suspend fun addTodo(expense: Todo)
    suspend fun updateTodo(expense: Todo)
    suspend fun deleteTodo(expense: Todo)
    fun getSortedDates(todosByDate: Map<String, List<Todo>>): List<String>
    suspend fun clearDatabase()
}