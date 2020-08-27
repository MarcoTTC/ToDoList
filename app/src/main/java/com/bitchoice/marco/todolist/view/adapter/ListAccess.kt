package com.bitchoice.marco.todolist.view.adapter

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
interface ListAccess<T> {
    fun setList(toDoTaskList: List<T>)

    fun addToList(newTask: T)

    fun removeFromList(task: T)

    fun clearList()
}