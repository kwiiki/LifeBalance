package com.example.lifebalance.screens.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lifebalance.data.expense.Expense
import com.example.lifebalance.data.expense.ExpenseType
import com.example.lifebalance.data.expense.TransactionType
import com.example.lifebalance.data.expense.addDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(modifier: Modifier, viewModel: ExpenseViewModel = viewModel()) {
    val expenses by viewModel.expense.collectAsState()

    val totalBalance by viewModel.totalBalance.collectAsState()
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()

    val (dialogOpen, setDialogOpen) = remember { mutableStateOf(false) }
  //  val (title, setTitle) = remember { mutableStateOf("") }
  //  val (price, setPrice) = remember { mutableStateOf("") }
  //  val (priceError, setPriceError) = remember { mutableStateOf(false) }
  //  val (description, setDescription) = remember { mutableStateOf(false) }

    if (dialogOpen) {
        ExpenseDialogScreen(
            setDialogOpen = setDialogOpen,
            viewModel = viewModel
        )
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
            totalBalance,
            totalIncome,
            totalExpense,
            modifier = Modifier
                .padding(paddings)
        )
    }
}


@Composable
fun ExpenseBody(
    expenses: List<Expense>, totalBalance: Long,
    totalIncome: Long,
    totalExpense: Long, modifier: Modifier
) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxHeight(0.3f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Total Balance: $totalBalance tg",
                        fontSize = 19.sp,
                        color = Color.White
                    )
                    Spacer(
                        modifier = Modifier
                            .height(32.dp)
                            .width(32.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SummaryMiniCard(
                            color = Color(0xFF00CB51),
                            imageVector = Icons.Outlined.ArrowDownward,
                            heading = "Income",
                            content = "+$totalIncome tg"
                        )
                        SummaryMiniCard(
                            color = Color(0xFFCB0000),
                            imageVector = Icons.Outlined.ArrowUpward,
                            heading = "Expense",
                            content = "-$totalExpense tg"
                        )
                    }


                }

            }
        }
        Spacer(
            modifier = Modifier
                .height(14.dp)
                .width(4.dp)
        )

        if (expenses.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                Alignment.TopCenter
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        text = "You haven't got transactions",
                        fontSize = 27.sp,
                        color = Color.White
                    )
                }

            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(expenses) { exp ->
                    ExpenseItem(expense = exp)

                }
            }
        }

    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
//            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (expense.transactionType.name == "EXPENSE") {
                Icons.Outlined.SouthWest
            } else {
                Icons.Outlined.NorthEast
            },
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = expense.title,
                    color = Color.White,
                    fontSize = 16.sp,
                )

                Text(
                    modifier = Modifier.width(70.dp),
                    text = expense.transactionType.name,
                    color = Color.White.copy(0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = expense.price.toString() + "tg",
                    color = Color.White,
                    fontSize = 16.sp,
                )

                Text(
                    text = expense.addDate,
                    color = Color.White.copy(0.7f),
                )
            }
        }

    }

}

@Preview
@Composable
fun ExpenseScreenPreview() {
    ExpenseItem(Expense(1, "wefw", 55, TransactionType.EXPENSE, ExpenseType.EDUCATION, "efwe"))
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
            Icon(
                imageVector = imageVector,
                contentDescription = heading,
                modifier = Modifier.size(45.dp),
                tint = color
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Column() {
            Text(
                text = heading,
                color = Color.White.copy(0.7f),
                fontSize = 16.sp,
            )
            Text(
                text = content, color = Color.White
            )

        }
    }
}


