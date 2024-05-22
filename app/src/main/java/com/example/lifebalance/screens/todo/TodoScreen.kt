@file:OptIn(
    ExperimentalPagerApi::class, ExperimentalPagerApi::class, ExperimentalPagerApi::class,
    ExperimentalPagerApi::class
)

package com.example.lifebalance.screens.todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalMaterial3Api
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val todosByDate by viewModel.todosByDate.collectAsState(initial = emptyMap())
    val isLoading by viewModel.isLoading.collectAsState()
    val (dialogOpen, setDialogOpen) = remember { mutableStateOf(false) }
    if (dialogOpen) {
        val (title, setTitle) = remember {
            mutableStateOf("")
        }
        val (description, setDescription) = remember {
            mutableStateOf("")
        }
        TodoDialog(
            title,
            setTitle,
            description,
            setDescription,
            setDialogOpen,
            viewModel
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.secondary,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "To do",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White
                    )
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = Color.Black,
                    navigationIconContentColor = Color.Cyan,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.DarkGray
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { setDialogOpen(true) },
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(strokeWidth = 5.dp, color = Color.White, progress = 0.5f)
            } else {
                if (todosByDate.isNotEmpty()) {
                    ViewPager(
                        dates = viewModel.getSortedDates(),
                        todosByDate = todosByDate,
                        viewModel = viewModel
                    )
                } else {
                    Text(
                        text = "No to do Yet!",
                        color = Color.White,
                        fontSize = 50.sp
                    )
                }
            }
        }
    }
}
