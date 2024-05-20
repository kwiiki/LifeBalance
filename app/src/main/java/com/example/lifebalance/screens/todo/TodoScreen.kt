@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class, ExperimentalPagerApi::class,
    ExperimentalPagerApi::class
)

package com.example.lifebalance.screens.todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lifebalance.data.todo.Todo
import com.example.lifebalance.data.todo.addDate
import com.example.lifebalance.repositories.getTodayDate
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val todosByDate by viewModel.todosByDate.collectAsState()
    val (dialogOpen, setDialogOpen) = remember { mutableStateOf(false) }
    if (dialogOpen) {
        val (title, setTitle) = remember {
            mutableStateOf("")
        }
        val (description, setDescription) = remember {
            mutableStateOf("")
        }
        val (priceError, setPriceError) = remember {
            mutableStateOf(false)
        }

        TodoDialog(
            title,
            setTitle,
            description,
            setDescription,
            priceError,
            setPriceError,
            dialogOpen,
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewPager(
    dates: List<String>,
    todosByDate: Map<String, List<Todo>>,
    viewModel: TodoViewModel
) {
    val pagerState = rememberPagerState(
        initialPage = if (dates.contains(getTodayDate())) dates.indexOf(getTodayDate()) else 0
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize(0.95f),
        count = dates.size,
        itemSpacing = 16.dp,
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) { page ->
        val date = dates[page]
        val todos = todosByDate[date] ?: emptyList()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = date,
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }
                items(todos) { todo ->
                    TodoItem(
                        todo = todo,
                        onClick = { viewModel.updateTodo(todo.copy(done = !todo.done)) },
                        onDelete = { viewModel.deleteTodo(todo) },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TodoItem(todo: Todo, onClick: () -> Unit, onDelete: () -> Unit) {
    val color by animateColorAsState(
        targetValue = if (todo.done) Color(0xff24d65f) else Color(0xffff6363),
        animationSpec = tween(500), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ), contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()

                .clip(RoundedCornerShape(5.dp))
                .background(color)
                .clickable {
                    onClick()
                }
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(modifier = Modifier) {
                        AnimatedVisibility(
                            visible = todo.done,
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = color)
                        }
                    }
                    Row(modifier = Modifier) {
                        AnimatedVisibility(
                            visible = !todo.done,
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = color)
                        }
                    }
                }
                Column {
                    Text(
                        text = todo.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = todo.description,
                        fontSize = 12.sp,
                        color = Color(0xffebebeb)
                    )


                }
            }

            Box(
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            onDelete()
                        })

            }


        }
        Text(
            text = todo.addDate,
            modifier = Modifier.padding(4.dp),
            color = Color(0xffebebeb),
            fontSize = 14.sp
        )

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun PreviewExpenseScreen() {
    TodoScreen()
}