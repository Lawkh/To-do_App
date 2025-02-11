package com.mmur.cursocompose.todoapp.addtasks.domain.useCases

import com.mmur.cursocompose.todoapp.addtasks.data.repository.TaskRepository
import com.mmur.cursocompose.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke(): Flow<List<TaskModel>> {
         return taskRepository.tasks
    }
}