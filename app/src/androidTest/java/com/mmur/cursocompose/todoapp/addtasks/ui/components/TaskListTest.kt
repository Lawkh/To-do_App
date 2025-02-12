package com.mmur.cursocompose.todoapp.addtasks.ui.components

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.mmur.cursocompose.todoapp.Hilt_MainActivity
import com.mmur.cursocompose.todoapp.MainActivity
import com.mmur.cursocompose.todoapp.addtasks.ui.model.TaskModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication

@HiltAndroidTest
class TaskListTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    private val data = listOf(
        TaskModel(task = "Hacer ejercicio", selected = true, id = 1718123456789),
        TaskModel(task = "Comprar víveres", selected = false, id = 1718123456790),
        TaskModel(task = "Llamar al banco", selected = false, id = 1718123456791),
        TaskModel(task = "Estudiar Kotlin", selected = true, id = 1718123456792),
        TaskModel(task = "Leer un libro", selected = false, id = 1718123456793)
    )

    @Test
    fun myFirstTest(){
        composeTestRule.activity.setContent {
            TasksList(data)
        }
        composeTestRule.onNodeWithText(data.first().task, ignoreCase = true).assertExists()
        composeTestRule.onNodeWithTag("task_list").onChildren().assertCountEquals(data.size)
        composeTestRule.onAllNodesWithTag("task_item").assertCountEquals(data.size)

    }


}