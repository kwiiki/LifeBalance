package com.example.lifebalance.repositories

import com.example.lifebalance.data.Todo
import kotlinx.coroutines.flow.Flow
import java.util.SortedMap

interface TodoRepository {

    suspend fun getTodo(): Flow<List<Todo>>
    suspend fun getTodosByDate(): Flow<HashMap<String, List<Todo>>>
    suspend fun addTodo(expense: Todo)
    suspend fun updateTodo(expense: Todo)
    suspend fun deleteTodo(expense: Todo)
}