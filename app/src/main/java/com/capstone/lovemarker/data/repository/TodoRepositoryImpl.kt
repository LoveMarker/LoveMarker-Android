package com.capstone.lovemarker.data.repository

import android.app.Application
import androidx.room.Room
import com.capstone.lovemarker.data.source.TodoDao
import com.capstone.lovemarker.data.source.TodoDatabase
import com.capstone.lovemarker.domain.model.Todo
import com.capstone.lovemarker.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(application: Application) : TodoRepository {
    private val db = Room.databaseBuilder(
        application.applicationContext,
        TodoDatabase::class.java,
        "todo-db"
    ).build()

    override fun observeTodoList(): Flow<List<Todo>> =
        db.todoDao().getTodoList()

    override suspend fun addTodo(todo: Todo) {
        db.todoDao().insertTodo(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        db.todoDao().updateTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        db.todoDao().deleteTodo(todo)
    }
}
