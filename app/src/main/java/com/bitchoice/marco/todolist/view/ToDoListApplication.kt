package com.bitchoice.marco.todolist.view

import android.app.Application
import androidx.room.Room
import com.bitchoice.marco.todolist.model.room.ToDoListDatabase

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
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