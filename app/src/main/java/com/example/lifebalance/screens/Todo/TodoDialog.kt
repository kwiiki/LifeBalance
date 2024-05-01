package com.example.lifebalance.screens.Todo

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lifebalance.data.Todo
import com.example.lifebalance.screens.TimePickerDialog
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun TodoDialog(
    title: String,
    setTitle: (String) -> Unit,
    description: String,
    setDescription: (String) -> Unit,
    priceError: Boolean,
    setPriceError: (Boolean) -> Unit,
    dialogOpen: Boolean,
    setDialogOpen: (Boolean) -> Unit,
    viewModel: TodoViewModel
) {
    val showDialog = mutableStateOf(false)
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    val context = LocalContext.current
    var selectedDateTimeMillis: Long = 1

    Dialog(onDismissRequest = { setDialogOpen(false) }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { setTitle(it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Title")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { newDescription ->
                    setDescription(newDescription)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Description")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.White),
                isError = priceError
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(text = "Choose a time", color = Color.White)
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { /*TODO*/ },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedDate = datePickerState.selectedDateMillis?.let {
                                    LocalDate.ofEpochDay(it / 86400000)
                                }
                                if (selectedDate != null && selectedDate.isAfter(LocalDate.now().minusDays(1))) {
                                    showDatePicker = false
                                    showTimePicker = true
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Selected date should be after today, please select again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            if (showTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { "w" },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedDate = datePickerState.selectedDateMillis?.let {
                                    LocalDate.ofEpochDay(it / 86400000)
                                }
                                val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)


                                if (selectedDate != null && selectedDate.isAfter(LocalDate.now().minusDays(1))) {
                                    if (selectedDate.isEqual(LocalDate.now()) && selectedTime.isBefore(LocalTime.now())) {
                                        Toast.makeText(
                                            context,
                                            "Selected time should be after the current time, please select again",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        selectedDateTime = selectedDate.atTime(selectedTime)
                                        selectedDateTimeMillis = selectedDateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()!!
                                        Toast.makeText(
                                            context,
                                            "Selected date $selectedDateTime saved",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        showTimePicker = false
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Selected date should be after today, please select again",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showTimePicker = false
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                ) {
                    TimePicker(state = timePickerState)
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty() && !priceError) {
                        viewModel.addTodo(
                            Todo(
                                title = title,
                                description = description,
                                added = selectedDateTimeMillis,
                                done = false
                            )
                        )
                        setDialogOpen(false)
                    }
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "Submit",
                    color = Color.White
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewTodoDialog() {
    TodoDialog(
        title = "Sample Title",
        setTitle = { /* TODO: Implement */ },
        description = "100",
        setDescription = { /* TODO: Implement */ },
        priceError = false,
        setPriceError = { /* TODO: Implement */ },
        dialogOpen = true,
        setDialogOpen = { /* TODO: Implement */ },
        viewModel = TodoViewModel() // Замените на ваш экземпляр TodoViewModel
    )
}