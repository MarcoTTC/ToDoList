package com.bitchoice.marco.todolist.model

import java.util.*

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
class ToDoList {
    private val ids: ArrayList<Int> = ArrayList()
    private val notes: ArrayList<String> = ArrayList()

    fun getId(pos: Int): Int {
        return ids[pos]
    }

    fun getNote(pos: Int): String {
        return notes[pos]
    }

    fun size(): Int {
        return ids.size
    }

    fun add(id: Int, note: String) {
        ids.add(0, id)
        notes.add(0, note)
    }

    fun remove(pos: Int) {
        ids.removeAt(pos)
        notes.removeAt(pos)
    }

    fun clear() {
        ids.clear()
        notes.clear()
    }
}