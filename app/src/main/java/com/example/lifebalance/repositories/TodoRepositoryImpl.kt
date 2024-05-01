package com.example.lifebalance.repositories

import com.example.lifebalance.data.Todo
import com.example.lifebalance.data.TodoDatabase
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val todoDatabase: TodoDatabase) : TodoRepository {
    private val dao = todoDatabase.todoDao()
    override suspend fun getTodo(): Flow<List<Todo>> = dao.getTodo()

    override suspend fun addTodo(expense: Todo) = dao.addTodo(expense)

    override suspend fun updateTodo(expense: Todo) = dao.updateTodo(expense)

    override suspend fun deleteTodo(expense: Todo) = dao.deleteTodo(expense)

}