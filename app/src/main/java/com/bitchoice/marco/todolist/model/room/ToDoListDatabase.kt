package com.bitchoice.marco.todolist.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDoTask::class), version = 1)
abstract class ToDoListDatabase : RoomDatabase() {
    abstract fun getToDoTaskDao(): ToDoTaskDao
}