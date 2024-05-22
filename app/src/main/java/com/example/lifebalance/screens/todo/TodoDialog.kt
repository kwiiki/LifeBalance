package com.example.lifebalance.screens.todo

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.lifebalance.data.todo.Todo
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
    setDialogOpen: (Boolean) -> Unit,
    viewModel: TodoViewModel
) {
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
                onValueChange = { setDescription(it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Description (optional)")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                ),
                textStyle = TextStyle(color = Color.White),
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
                                if (selectedDate != null && selectedDate.isAfter(
                                        LocalDate.now().minusDays(1)
                                    )
                                ) {
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
                            Text("OK", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDatePicker = false
                            }
                        ) {
                            Text("Cancel", color = Color.White)
                        }
                    },
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            if (showTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val selectedDate = datePickerState.selectedDateMillis?.let {
                                    LocalDate.ofEpochDay(it / 86400000)
                                }
                                val selectedTime =
                                    LocalTime.of(timePickerState.hour, timePickerState.minute)


                                if (selectedDate != null && selectedDate.isAfter(
                                        LocalDate.now().minusDays(1)
                                    )
                                ) {
                                    if (selectedDate.isEqual(LocalDate.now()) && selectedTime.isBefore(
                                            LocalTime.now()
                                        )
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "Selected time should be after the current time, please select again",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        selectedDateTime = selectedDate.atTime(selectedTime)
                                        selectedDateTimeMillis =
                                            selectedDateTime?.atZone(ZoneId.systemDefault())
                                                ?.toInstant()?.toEpochMilli()!!
                                        Toast.makeText(
                                            context,
                                            "Selected date saved",
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
                            Text("Cancel", color = Color.White)
                        }
                    }
                ) {
                    TimePicker(state = timePickerState)
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Button(
                onClick = {
                    if (title.isNotEmpty()) {
                        if (selectedDateTime == null) {
                            Toast.makeText(
                                context,
                                "Please select a date and time",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
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
@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismissRequest: () -> Unit,
    confirmButton: @Composable (() -> Unit),
    dismissButton: @Composable (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = containerColor
                ),
            color = containerColor
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material.Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}
