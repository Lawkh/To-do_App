package com.mmur.cursocompose.todoapp.addtasks.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class TaskListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myFirstTest(){
        composeTestRule.setContent {
            TasksList(listOf())
        }
    }
}