package com.example.lifebalance.data

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class Expense(
    val id:Int,
    val title:String,
    val amount:String,
    val transactionType:TransactionType,
    val note:String,
    val date:Long = System.currentTimeMillis()
)

val Expense.addDate:String
    @SuppressLint("SimpleDateFormat")
    get() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date

        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

