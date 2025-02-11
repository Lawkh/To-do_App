package com.mmur.cursocompose.todoapp.addtasks.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mmur.cursocompose.todoapp.addtasks.data.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideTaskDao(todoDatabase: TodoDatabase) = todoDatabase.taskDao()

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "TodoDatabase"
        ).build()
    }
}