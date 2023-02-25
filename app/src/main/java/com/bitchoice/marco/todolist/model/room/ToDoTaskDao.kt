package com.bitchoice.marco.todolist.model.room

import androidx.room.*

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 */
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