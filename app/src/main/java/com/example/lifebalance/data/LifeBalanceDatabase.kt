package com.example.lifebalance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lifebalance.data.expense.Expense
import com.example.lifebalance.data.expense.ExpenseDao
import com.example.lifebalance.data.todo.Todo
import com.example.lifebalance.data.todo.TodoDao

@Database(entities = [Todo::class,Expense::class], version = 1, exportSchema = false)
abstract class LifeBalanceDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun expenseDao():ExpenseDao

}