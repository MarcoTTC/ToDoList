package com.bitchoice.marco.todolist.model.room

import androidx.room.*

@Dao
interface ToDoTaskDao {
    @Query("SELECT * FROM ToDoTask ORDER BY uid DESC")
    suspend fun getAll(): List<ToDoTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg tasks: ToDoTask)

    @Delete
    suspend fun delete(task: ToDoTask)

    @Query("DELETE FROM ToDoTask")
    suspend fun clear()

    @Query("SELECT uid FROM ToDoTask WHERE note = :givenNote")
    suspend fun getUidFromNote(givenNote: String): Int
}