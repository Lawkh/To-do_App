package com.mmur.cursocompose.todoapp.addtasks.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.mmur.cursocompose.todoapp.addtasks.ui.model.TaskModel
import com.mmur.cursocompose.todoapp.ui.theme.primaryGreen
import com.mmur.cursocompose.todoapp.ui.theme.secondaryWhite

@Composable
fun TasksScreen(taskViewModel: TaskViewModel, modifier: Modifier) {
    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TaskUiState>(
        initialValue = TaskUiState.Loading,
        key1 = lifeCycle,
        key2 = taskViewModel
    ) {
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            taskViewModel.taskState.collect { value = it }
        }

    }
    when (uiState) {
        is TaskUiState.Error -> {

        }

        is TaskUiState.Loading -> {
            CircularProgressIndicator()
        }

        is TaskUiState.Success -> {
            Box(modifier = modifier.fillMaxSize()) {

                val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)
                if (showDialog) {
                    AddTaskDialog(
                        true,
                        onDismiss = { taskViewModel.onDialogClose() },
                        onTaskAdded = { taskViewModel.onTaskCreated(it) })
                }
                TasksList((uiState as TaskUiState.Success).tasks)
                FabDialog(
                    onClickFab = { taskViewModel.onShowDialogClick() },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }
    }


}

@Composable
fun TasksList(myTasks: List<TaskModel>) {
    LazyColumn {
        items(
            count = myTasks.size,
            key = { index -> myTasks[index].id },
            contentType = { "task" }) {
            TaskItem(myTasks[it]  )
        }
    }
}

@Composable
fun TaskItem(task: TaskModel, taskViewModel: TaskViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    taskViewModel.onItemRemove(task)
                })

            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, color = primaryGreen),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.task, Modifier
                    .weight(1f)
                    .padding(4.dp)
            )
            Checkbox(
                checked = task.selected,
                onCheckedChange = { taskViewModel.onCheckBoxSelected(task.copy(selected = it)) })
        }
    }
}

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