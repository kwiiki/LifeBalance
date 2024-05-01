package com.example.lifebalance

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifebalance.navigation.BottomNavigationItem
import com.example.lifebalance.navigation.Screens
import com.example.lifebalance.screens.Expense.ExpenseScreen
import com.example.lifebalance.screens.Todo.TodoScreen
import com.example.lifebalance.screens.Just


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LifeBalanceApp() {
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->

                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(navigationItem.label)
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Todo.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screens.Todo.route) {
                TodoScreen()
            }
            composable(Screens.Expense.route) {
                ExpenseScreen(modifier = Modifier.padding(16.dp))
            }
            composable(Screens.Profile.route) {
                ProfileScreen()
            }
        }
    }
}



@Composable
fun ProfileScreen() {
    Column {
        Text(text = "profile Screen")
    }
}
