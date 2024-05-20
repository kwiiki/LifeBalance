package com.example.lifebalance.data.expense

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Entity()
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "price")
    val price:Int,
    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,
    @ColumnInfo(name = "expense_type")
    val expenseType: ExpenseType,
    @ColumnInfo(name = "description")
    val description:String,
    @ColumnInfo(name = "date")
    val date:Long = System.currentTimeMillis()
)

val Expense.addDate:String
    @SuppressLint("SimpleDateFormat")
    get() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

