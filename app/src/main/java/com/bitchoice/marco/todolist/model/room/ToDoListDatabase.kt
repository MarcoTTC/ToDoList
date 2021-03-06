package com.bitchoice.marco.todolist.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
@Database(entities = arrayOf(ToDoTask::class), version = 1)
abstract class ToDoListDatabase : RoomDatabase() {
    abstract fun getToDoTaskDao(): ToDoTaskDao
}