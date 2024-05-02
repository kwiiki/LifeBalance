package com.example.lifebalance.repositories

import com.example.lifebalance.data.Todo
import com.example.lifebalance.data.TodoDatabase
import com.example.lifebalance.data.getDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


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

    override fun getSortedDates(todosByDate: HashMap<String, List<Todo>>): List<String> {
        val todayDate = getTodayDate()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        val dates = todosByDate.keys.toList()
            .map { dateFormat.parse(it) }
            .filterNotNull()
            .filter { it.time >= (dateFormat.parse(todayDate)?.time ?: 0L) }

        return dates.sortedWith(
            compareBy<Date> { date ->
                when {
                    date == dateFormat.parse(todayDate) -> 0
                    date.before(dateFormat.parse(todayDate)) -> 1
                    else -> 2
                }
            }.thenBy { it }
        ).map { dateFormat.format(it) }
    }

}

fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}