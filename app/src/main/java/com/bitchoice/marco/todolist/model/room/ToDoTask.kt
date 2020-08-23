package com.bitchoice.marco.todolist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoTask(
        @PrimaryKey(autoGenerate = true) var uid: Int,
        @ColumnInfo(name = "note") var note: String?
)