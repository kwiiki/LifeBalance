package com.example.lifebalance.data.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Insert
    fun addTodo(todo: Todo)

    @Query("SELECT * FROM 'todo'")
    fun getTodo():Flow<List<Todo>>

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
    @Query("DELETE FROM 'todo'")
    suspend fun clearDatabase()
}