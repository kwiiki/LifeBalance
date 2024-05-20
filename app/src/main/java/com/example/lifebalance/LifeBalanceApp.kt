@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.example.lifebalance

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifebalance.navigation.BottomNavigationItem
import com.example.lifebalance.navigation.Screens
import com.example.lifebalance.preferences.PreferencesManager
import com.example.lifebalance.screens.expense.ExpenseScreen
import com.example.lifebalance.screens.onboarding.OnBoarding
import com.example.lifebalance.screens.profile.ProfileScreen
import com.example.lifebalance.screens.profile.StatisticsScreen
import com.example.lifebalance.screens.todo.TodoScreen
import com.google.accompanist.pager.ExperimentalPagerApi


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LifeBalanceApp(context:Context) {
    val preferencesManager = PreferencesManager(context)
    val isOnBoardingShown = preferencesManager.isOnBoardingShown()
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
            composable(Screens.OnBoarding.route) {
                OnBoarding(navController = navController,preferencesManager = PreferencesManager(
                    LocalContext.current)
                )
            }
            composable(Screens.Todo.route) {
                TodoScreen()
            }
            composable(Screens.Expense.route) {
                ExpenseScreen(modifier = Modifier.padding(16.dp))
            }
            composable(Screens.Profile.route) {
                ProfileScreen(navController = navController)
            }
            composable(Screens.StatisticsScreen.route) {
                val data = listOf(
                    Pair("Mon", 1200.0),
                    Pair("Tue", 2000.0),
                    Pair("Wed", 1504.0),
                    Pair("Thu", 800.0),
                    Pair("Fri", 180.0),
                    Pair("Sat", 250.0),
                    Pair("Sun", 920.0)
                )
                StatisticsScreen(data = data, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp))
            }
        }
    }
}



