package com.mmur.cursocompose.todoapp.addtasks.data.database

data class TaskEntity(
    val task: String,
    var selected: Boolean = false,
    val id: Long = System.currentTimeMillis()
)