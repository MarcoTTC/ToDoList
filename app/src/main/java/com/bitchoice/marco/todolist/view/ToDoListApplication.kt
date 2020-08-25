package com.bitchoice.marco.todolist.view

import android.app.Application
import androidx.room.Room
import com.bitchoice.marco.todolist.model.room.ToDoListDatabase

class ToDoListApplication: Application() {

    lateinit var database: ToDoListDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
                applicationContext,
                ToDoListDatabase::class.java,
                "ToDoList-Database"
        ).build()
    }
}