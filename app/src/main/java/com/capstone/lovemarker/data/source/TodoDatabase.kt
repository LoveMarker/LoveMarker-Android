package com.capstone.lovemarker.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.lovemarker.domain.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
