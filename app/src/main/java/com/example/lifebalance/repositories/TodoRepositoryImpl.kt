package com.example.lifebalance.repositories

import com.example.lifebalance.data.Todo
import com.example.lifebalance.data.TodoDatabase
import com.example.lifebalance.data.addDate
import com.example.lifebalance.data.getDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.SortedMap


class TodoRepositoryImpl(private val todoDatabase: TodoDatabase) : TodoRepository {
    private val dao = todoDatabase.todoDao()
    override suspend fun getTodo(): Flow<List<Todo>> = dao.getTodo()
    override suspend fun getTodosByDate(): Flow<HashMap<String, List<Todo>>> {
        return dao.getTodo().map { todos ->
            val todosByDate = HashMap<String, List<Todo>>()

            todos.forEach { todo ->
                val date = todo.getDate()
                val list = todosByDate[date] ?: emptyList()
                todosByDate[date] = list + todo
            }

            todosByDate
        }
    }

    override suspend fun addTodo(expense: Todo) = dao.addTodo(expense)

    override suspend fun updateTodo(expense: Todo) = dao.updateTodo(expense)

    override suspend fun deleteTodo(expense: Todo) = dao.deleteTodo(expense)

}

fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(calendar.time)
}