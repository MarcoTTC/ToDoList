package com.bitchoice.marco.todolist.presenter

import com.bitchoice.marco.todolist.model.room.ToDoTask

interface ToDoTaskListAccess {
    fun setToDoTaskList(toDoTaskList: List<ToDoTask>)

    fun addTask(newTask: ToDoTask)

    fun removeTask(task: ToDoTask)

    fun clearList()
}