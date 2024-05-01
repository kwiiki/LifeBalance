package com.example.lifebalance.navigation

sealed class Screens(val route : String) {
    data object Expense : Screens("expense_route")
    data object Todo : Screens("todo_route")
    data object Profile : Screens("profile_route")
}