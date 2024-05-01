package com.example.lifebalance.screens.Expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lifebalance.data.Expense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(modifier: Modifier, viewModel: ExpenseViewModel = viewModel()) {
    val expenses by viewModel.expense.collectAsState()
    val (dialogOpen, setDialogOpen) = remember {
        mutableStateOf(false)
    }
    Scaffold(containerColor = MaterialTheme.colorScheme.secondary,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Expense",
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

        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { setDialogOpen(true) },
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = null)

            }
        }) { paddings ->
        ExpenseBody(
            expenses,
            modifier = Modifier
                .padding(paddings)

        )
    }

}


@Composable
fun ExpenseBody(expenses: List<Expense>, modifier: Modifier) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(modifier = Modifier.padding(16.dp, 0.dp)) {

            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colorScheme.primary
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total Balance:", fontSize = 16.sp, color = Color.White
                    )
                    Text(
                        text = "151559", fontSize = 40.sp, color = Color.White
                    )
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .width(16.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SummaryMiniCard(
                            color = Color(0xFF00CB51),
                            imageVector = Icons.Outlined.ArrowDownward,
                            heading = "Income",
                            content = "+788"
                        )
                        SummaryMiniCard(
                            color = Color(0xFFCB0000),
                            imageVector = Icons.Outlined.ArrowUpward,
                            heading = "Expense",
                            content = "-$6565"
                        )
                    }


                }

            }
        }
        Spacer(
            modifier = Modifier
                .height(24.dp)
                .width(4.dp)
            //             .background(Color.Black)
        )

        if (expenses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                Alignment.TopCenter
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "You haven't got transactions",
                        fontSize = 27.sp
                    )
                }

            }
        } else {
            LazyColumn {

            }
        }

    }
}

@Composable
fun ExpernseItem() {

}

@Preview
@Composable
fun ExpenseScreenPreview() {
    ExpenseScreen(modifier = Modifier)

}


@Composable
fun SummaryMiniCard(
    color: Color, imageVector: ImageVector, heading: String, content: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFFFAD9E6))
        ) {
            androidx.compose.material.Icon(
                imageVector = imageVector,
                contentDescription = heading,
                modifier = Modifier.size(40.dp),
                tint = color
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Column() {
            androidx.compose.material.Text(
                text = heading,
                color = Color.White.copy(0.7f),
                fontSize = 16.sp,
            )
            androidx.compose.material.Text(
                text = content, color = Color.White
            )

        }
    }
}