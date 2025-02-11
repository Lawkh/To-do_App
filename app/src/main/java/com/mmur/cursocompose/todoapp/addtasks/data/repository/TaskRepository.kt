package com.mmur.cursocompose.todoapp.addtasks.data.repository

import com.mmur.cursocompose.todoapp.addtasks.data.database.TaskDao
import com.mmur.cursocompose.todoapp.addtasks.data.database.TaskEntity
import com.mmur.cursocompose.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskdao: TaskDao) {

    val tasks: Flow<List<TaskModel>> = taskdao.getAllTasks()
        .map { items ->
            items.map { entity ->
                TaskModel(
                    id = entity.id,
                    task = entity.task,
                    selected = entity.selected
                )
            }
        }

    suspend fun upsert(taskModel: TaskModel) {
        taskdao.upsertTask(
            TaskEntity(
                id = taskModel.id,
                task = taskModel.task,
                selected = taskModel.selected
            )
        )
    }

    suspend fun delete(taskModel: TaskModel) {
        taskdao.deleteTask(
            TaskEntity(
                id = taskModel.id,
                task = taskModel.task,
                selected = taskModel.selected
            )
        )
    }
}