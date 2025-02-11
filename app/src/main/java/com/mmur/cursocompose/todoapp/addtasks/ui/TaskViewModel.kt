package com.mmur.cursocompose.todoapp.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmur.cursocompose.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class TaskViewModel @Inject constructor() : ViewModel() {
    private var _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private var _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onTaskCreated(task: String) {
        onDialogClose()
        _tasks.add(TaskModel(task))
    }

    fun onCheckBoxSelected(task: TaskModel) {
        val index = _tasks.indexOf(task)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(task: TaskModel) {
        _tasks.removeIf { it.id == task.id }
    }


}