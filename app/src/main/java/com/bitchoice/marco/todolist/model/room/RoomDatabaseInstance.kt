package com.bitchoice.marco.todolist.model.room

import android.content.Context
import androidx.room.Room

class RoomDatabaseInstance {

    companion object {
        private var database: ToDoListDatabase? = null

        fun getInstance(applicationContext: Context): ToDoListDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    applicationContext,
                    ToDoListDatabase::class.java,
                    "ToDoList-Database"
                ).build()
            }
            return database as ToDoListDatabase
        }
    }
}