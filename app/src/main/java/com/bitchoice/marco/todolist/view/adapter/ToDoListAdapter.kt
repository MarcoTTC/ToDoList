package com.bitchoice.marco.todolist.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.view.adapter.viewholder.ToDoTaskViewHolder

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 */
class ToDoListAdapter: RecyclerView.Adapter<ToDoTaskViewHolder>(), ListAccess<ToDoTask> {

    private var toDoList: MutableList<ToDoTask>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoTaskViewHolder {
        return ToDoTaskViewHolder.inflate(parent, this)
    }

    override fun onBindViewHolder(holder: ToDoTaskViewHolder, position: Int) {
        val task = toDoList!![position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return if (toDoList == null) {
            0
        } else {
            toDoList!!.size
        }
    }

    override fun setList(toDoTaskList: List<ToDoTask>) {
        toDoList = toDoTaskList.toMutableList()
        notifyDataSetChanged()
    }

    override fun addToList(newTask: ToDoTask) {
        toDoList?.let {
            it.add(0, newTask)
            notifyItemInserted(0)
        }
    }

    override fun removeFromList(task: ToDoTask) {
        toDoList?.let {
            val position = it.indexOf(task)
            it.remove(task)
            notifyItemRemoved(position)
        }
    }

    override fun clearList() {
        toDoList?.let {
            it.clear()
            notifyDataSetChanged()
        }
    }
}