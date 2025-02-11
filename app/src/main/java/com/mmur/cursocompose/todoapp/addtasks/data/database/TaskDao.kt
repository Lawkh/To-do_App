package com.mmur.cursocompose.todoapp.addtasks.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Upsert
    suspend fun upsertTask(item: TaskEntity)

    @Delete
    suspend fun deleteTask(item: TaskEntity)
}