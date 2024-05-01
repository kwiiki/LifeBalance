package com.example.lifebalance.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.ShoppingCart,
    val route : String = ""
) {

    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Todo",
                icon = Icons.Filled.Create,
                route = Screens.Todo.route
            ),
            BottomNavigationItem(
                label = "Expense",
                icon = Icons.Filled.ShoppingCart,
                route = Screens.Expense.route
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = Icons.Filled.Person,
                route = Screens.Profile.route
            ),
        )
    }
}
