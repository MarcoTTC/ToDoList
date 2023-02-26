package com.bitchoice.marco.todolist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under MIT License
 * and it's available at http://github.com/MarcoTTC/ToDoList
 */
@Entity
data class ToDoTask(
        @PrimaryKey(autoGenerate = true) var uid: Int,
        @ColumnInfo(name = "note") var note: String?
)