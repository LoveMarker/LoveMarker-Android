package com.capstone.lovemarker.domain.repository

import com.capstone.lovemarker.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun observeTodoList(): Flow<List<Todo>>

    suspend fun addTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)
}
