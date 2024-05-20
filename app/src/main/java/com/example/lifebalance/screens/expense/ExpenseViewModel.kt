    package com.example.lifebalance.screens.expense

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.lifebalance.data.expense.Expense
    import com.example.lifebalance.data.expense.TransactionType
    import com.example.lifebalance.repositories.ExpenseRepository
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch
    import org.koin.core.component.KoinComponent
    import org.koin.core.component.inject

    class ExpenseViewModel:ViewModel(),KoinComponent {

        private val repository:ExpenseRepository by inject()

        private val _expenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
        val expense = _expenses.asStateFlow()

        private val _totalBalance: MutableStateFlow<Long> = MutableStateFlow(0L)
        val totalBalance = _totalBalance.asStateFlow()

        private val _totalIncome: MutableStateFlow<Long> = MutableStateFlow(0L)
        val totalIncome = _totalIncome.asStateFlow()

        private val _totalExpense: MutableStateFlow<Long> = MutableStateFlow(0L)
        val totalExpense = _totalExpense.asStateFlow()

        init {
            getExpense()
            initializeTotals()
        }



        private fun getExpense(){
            viewModelScope.launch(Dispatchers.IO) {
                repository.getExpense().collect { data ->
                    _expenses.value = data
                }
            }
        }

        private fun initializeTotals() {
            viewModelScope.launch(Dispatchers.IO) {
                val expenses = repository.getAllExpenses()
                var totalBalance: Long = 0
                var totalIncome: Long = 0
                var totalExpense: Long = 0

                for (exp in expenses) {
                    when (exp.transactionType) {
                        TransactionType.INCOME -> {
                            totalBalance += exp.price.toLong()
                            totalIncome += exp.price.toLong()
                        }
                        TransactionType.EXPENSE -> {
                            totalBalance -= exp.price.toLong()
                            totalExpense += exp.price.toLong()
                        }
                    }
                }

                _totalBalance.value = totalBalance
                _totalIncome.value = totalIncome
                _totalExpense.value = totalExpense
            }
        }
        fun updateExpense(expense: Expense) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateExpense(expense)
                updateTotals(expense)
            }
        }

        fun addExpense(expense: Expense) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addExpense(expense)
          //      updateTotals(expense)
            }
        }
        private fun updateTotals(expense: Expense, isDeleting: Boolean = false) {
            val priceChange = if (isDeleting) -expense.price.toLong() else expense.price.toLong()
            _totalBalance.value += when (expense.transactionType) {
                TransactionType.INCOME -> priceChange
                TransactionType.EXPENSE -> -priceChange
            }
            _totalIncome.value += if (expense.transactionType == TransactionType.INCOME && !isDeleting) priceChange else 0
            _totalExpense.value += if (expense.transactionType == TransactionType.EXPENSE && !isDeleting) priceChange else 0
        }
        fun deleteExpense(expense: Expense) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteExpense(expense)
                updateTotals(expense, isDeleting = true)
            }
        }
    }