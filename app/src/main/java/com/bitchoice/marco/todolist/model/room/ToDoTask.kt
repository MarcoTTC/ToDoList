package com.bitchoice.marco.todolist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
@Entity
data class ToDoTask(
        @PrimaryKey(autoGenerate = true) var uid: Int,
        @ColumnInfo(name = "note") var note: String?
)