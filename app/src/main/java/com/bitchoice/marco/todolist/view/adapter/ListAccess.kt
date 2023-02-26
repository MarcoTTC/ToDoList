package com.bitchoice.marco.todolist.view.adapter

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under MIT License
 * and it's available at http://github.com/MarcoTTC/ToDoList
 */
interface ListAccess<T> {
    fun setList(toDoTaskList: List<T>)

    fun addToList(newTask: T)

    fun removeFromList(task: T)

    fun clearList()
}