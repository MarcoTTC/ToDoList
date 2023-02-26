package com.bitchoice.marco.todolist.model.room

import android.content.Context
import androidx.room.Room

/**
 * Created by Marco Tulio Todeschini Coelho on 25/02/23
 * This android app source code is licenced under MIT License
 * and it's available at http://github.com/MarcoTTC/ToDoList
 */
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