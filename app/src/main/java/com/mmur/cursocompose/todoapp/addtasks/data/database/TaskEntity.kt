package com.mmur.cursocompose.todoapp.addtasks.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Long,
    val task: String,
    var selected: Boolean = false
)