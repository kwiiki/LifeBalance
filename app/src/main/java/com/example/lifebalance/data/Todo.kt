package com.example.lifebalance.data

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Entity(tableName = "todo")
data class Todo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("done")
    val done: Boolean = false,

    @ColumnInfo("added")
    val added: Long = System.currentTimeMillis()
)
val Todo.addDate: String
    @SuppressLint("SimpleDateFormat")
    get() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = added

        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

fun Todo.getDate(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = added
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}

