package com.example.lifebalance.screens.Expense

import androidx.lifecycle.ViewModel
import com.example.lifebalance.data.Expense
import com.example.lifebalance.data.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExpenseViewModel:ViewModel() {

    private val _expenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val expense = _expenses.asStateFlow()
}