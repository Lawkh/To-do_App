package com.mmur.cursocompose.todoapp.addtasks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.mmur.cursocompose.todoapp.addtasks.ui.components.AddTaskDialog
import com.mmur.cursocompose.todoapp.addtasks.ui.components.FabDialog
import com.mmur.cursocompose.todoapp.addtasks.ui.components.TasksList

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




