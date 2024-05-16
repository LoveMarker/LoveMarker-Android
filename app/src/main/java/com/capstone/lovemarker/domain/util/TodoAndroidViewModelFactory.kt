package com.capstone.lovemarker.domain.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.lovemarker.data.repository.TodoRepositoryImpl
import com.capstone.lovemarker.domain.repository.TodoRepository
import com.capstone.lovemarker.ui.main.MainViewModel

class TodoAndroidViewModelFactory(
    private val application: Application,
    private val repository: TodoRepository = TodoRepositoryImpl(application)
) : ViewModelProvider.AndroidViewModelFactory(application) {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                application = application,
                todoRepository = repository
            ) as T
        }
        return super.create(modelClass)
    }
}