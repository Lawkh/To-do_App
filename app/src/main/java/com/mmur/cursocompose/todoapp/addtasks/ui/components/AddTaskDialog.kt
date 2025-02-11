package com.mmur.cursocompose.todoapp.addtasks.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mmur.cursocompose.todoapp.ui.theme.primaryGreen
import com.mmur.cursocompose.todoapp.ui.theme.secondaryWhite

@Composable
fun FabDialog(modifier: Modifier, onClickFab: () -> Unit) {
    FloatingActionButton(onClick = {
        onClickFab()
    }, modifier) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    if (show) {
        var myTask by remember { mutableStateOf("") }
        Dialog(onDismissRequest = { onDismiss() }) {

            Card(
                Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8F))
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        "Añade tu tarea",
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        value = myTask,
                        onValueChange = { myTask = it },
                        singleLine = true,
                        maxLines = 1
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onTaskAdded(myTask)
                            myTask = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = secondaryWhite,
                            containerColor = primaryGreen
                        )
                    ) {
                        Text("Añadir tarea")
                    }
                }
            }
        }
    }
}