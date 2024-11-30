package com.example.todoassignment.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoassignment.models.Todos
import com.example.todoassignment.todorepository.TodosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(private val repository: TodosRepository) : ViewModel() {

    val todosCompletedStateFlow = MutableStateFlow(emptyList<Todos>())
    val todosCompleted = todosCompletedStateFlow.asStateFlow()

    private val todosUnCompletedStateFlow = MutableStateFlow(emptyList<Todos>())
    val todosUnCompleted = todosUnCompletedStateFlow.asStateFlow()

    fun addTodo(todo: Todos) {
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }

    fun removeTodo(todo: Todos) {
        viewModelScope.launch {
            repository.removeTodo(todo)
        }
    }

    fun updateTodo(todo: Todos) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun allTodos() {
        viewModelScope.launch {
            repository.allTodos().flowOn(Dispatchers.IO).collect { todos ->
                todosCompletedStateFlow.update {
                    todos.filter { it.completed }
                }

                todosUnCompletedStateFlow.update {
                    todos.filter { !it.completed }
                }
            }
        }
    }
}