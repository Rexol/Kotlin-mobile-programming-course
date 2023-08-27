package com.example.todo.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.model.Todo
import com.example.todo.model.TodosApi
import kotlinx.coroutines.launch

sealed interface TodoUIState {
    data class Success(val todos: List<Todo>): TodoUIState
    object Error: TodoUIState
    object Loading: TodoUIState
}

class TodoViewModel: ViewModel() {
    var todoUIState: TodoUIState by mutableStateOf(TodoUIState.Loading)
        private set

    init {
        getTodosList()
    }

    private fun getTodosList() {
        viewModelScope.launch {
            var todosApi: TodosApi? = null
            try {
                todosApi = TodosApi!!.getInstance()
                todoUIState = TodoUIState.Success(todosApi.getTodos())
            } catch (e: Exception) {
                Log.d("TODOVIEWMODEL", e.message.toString())
                todoUIState = TodoUIState.Error
            }
        }
    }
}