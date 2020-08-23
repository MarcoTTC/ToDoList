package com.bitchoice.marco.todolist.model.room

import androidx.room.*

@Dao
interface ToDoTaskDao {
    @Query("SELECT * FROM ToDoTask ORDER BY uid DESC")
    fun getAll(): List<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tasks: ToDoTask)

    @Delete
    fun delete(task: ToDoTask)

    @Query("DELETE FROM ToDoTask")
    fun clear()

    @Query("SELECT uid FROM ToDoTask WHERE note = :givenNote")
    fun getUidFromNote(givenNote: String): Int
}