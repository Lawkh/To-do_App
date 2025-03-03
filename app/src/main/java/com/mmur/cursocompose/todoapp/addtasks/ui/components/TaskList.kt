package com.mmur.cursocompose.todoapp.addtasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mmur.cursocompose.todoapp.addtasks.ui.TaskViewModel
import com.mmur.cursocompose.todoapp.addtasks.ui.model.TaskModel
import com.mmur.cursocompose.todoapp.ui.theme.primaryGreen


@Composable
fun TasksList(myTasks: List<TaskModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag("task_list")
    ) {
        items(myTasks){
            TaskItem(it)
        }
    }
}

@Composable
fun TaskItem(task: TaskModel, taskViewModel: TaskViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .testTag("task_item")
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
                text = task.task,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp).testTag("task_text")
            )
            Checkbox(
                checked = task.selected,
                onCheckedChange = { taskViewModel.onCheckBoxSelected(task.copy(selected = it)) },
                modifier = Modifier.testTag("checkbox_task")
                )
        }
    }
}