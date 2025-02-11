package com.mmur.cursocompose.todoapp.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmur.cursocompose.todoapp.addtasks.domain.useCases.AddTaskUseCase
import com.mmur.cursocompose.todoapp.addtasks.domain.useCases.GetTaskUseCase
import com.mmur.cursocompose.todoapp.addtasks.ui.TaskUiState.*
import com.mmur.cursocompose.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTaskUseCase: GetTaskUseCase,
) : ViewModel() {

    val taskState: StateFlow<TaskUiState> =
        getTaskUseCase()
            .map(::Success)
            .catch {
                Error(it)
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)


    private var _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

//    private var _tasks = mutableStateListOf<TaskModel>()
//    val tasks: List<TaskModel> = _tasks

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onTaskCreated(task: String) {
        //prepare UI
        onDialogClose()

        //prepare DATA
        val taskModel = TaskModel(task)

        //Launch use case, that will update DB and get task list flow will get notified.
        viewModelScope.launch {
            addTaskUseCase(taskModel)
        }
        //_tasks.add(taskModel)
    }

    fun onCheckBoxSelected(task: TaskModel) {
//        val index = _tasks.indexOf(task)
//        _tasks[index] = _tasks[index].let {
//            it.copy(selected = !it.selected)
//        }
    }

    fun onItemRemove(task: TaskModel) {
//        _tasks.removeIf { it.id == task.id }
    }


}