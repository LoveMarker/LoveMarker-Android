package com.capstone.lovemarker.ui.main

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.lovemarker.domain.model.Todo
import com.capstone.lovemarker.domain.repository.TodoRepository
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val todoRepository: TodoRepository
) : AndroidViewModel(application) {
    private val _items = mutableStateOf(emptyList<Todo>())
    val items : State<List<Todo>> = _items

    private var recentDeleteTodo: Todo? = null

    fun addTodo(text: String) {
        viewModelScope.launch {
            todoRepository.addTodo(Todo(title = text))
        }
    }

    fun toggleDoneState(uid: Int) {
        val todo = _items.value.find { todo -> todo.uid == uid }
        todo?.let {
            viewModelScope.launch {
                todoRepository.updateTodo(
                    it.copy(isDone = !it.isDone).apply { // 완료 속성만 변경
                        this.uid = it.uid // uid 그대로 유지
                    }
                )
            }
        }
    }

    fun deleteTodo(uid: Int) {
        val todo = _items.value.find { todo -> todo.uid == uid }
        todo?.let {
            viewModelScope.launch {
                todoRepository.deleteTodo(it)
                recentDeleteTodo = it
            }
        }
    }

    fun restoreTodo() {
        viewModelScope.launch {
            todoRepository.addTodo(recentDeleteTodo ?: return@launch)
            recentDeleteTodo = null
        }
    }
}