package com.bitchoice.marco.todolist.view.adapter

interface ListAccess<T> {
    fun setList(toDoTaskList: List<T>)

    fun addToList(newTask: T)

    fun removeFromList(task: T)

    fun clearList()
}