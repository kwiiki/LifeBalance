package com.example.lifebalance.repositories

import com.example.lifebalance.data.todo.Todo
import com.example.lifebalance.data.LifeBalanceDatabase
import com.example.lifebalance.data.todo.getDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class TodoRepositoryImpl(private val lifeBalanceDatabase: LifeBalanceDatabase) : TodoRepository {
    private val dao = lifeBalanceDatabase.todoDao()

    private val _todosByDate = dao.getTodo()
        .map { todos ->
            val todosByDate = HashMap<String, List<Todo>>()

            todos.forEach { todo ->
                val date = todo.getDate()
                val list = todosByDate[date] ?: emptyList()
                todosByDate[date] = list + todo
            }

            todosByDate
        }
        .shareIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(),
            replay = 1
        )

    override suspend fun getTodo(): Flow<List<Todo>> = dao.getTodo()

    override suspend fun getTodosByDate(): Flow<HashMap<String, List<Todo>>> = _todosByDate

    override suspend fun addTodo(expense: Todo) = dao.addTodo(expense)

    override suspend fun updateTodo(expense: Todo) = dao.updateTodo(expense)

    override suspend fun deleteTodo(expense: Todo) = dao.deleteTodo(expense)
    override fun getSortedDates(todosByDate: Map<String, List<Todo>>): List<String> {
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
                    else -> 2
                }
            }.thenBy { it }
        ).map { dateFormat.format(it) }
    }

    override suspend fun clearDatabase() {
        dao.clearDatabase()
    }

}

fun getTodayDate(): String {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}