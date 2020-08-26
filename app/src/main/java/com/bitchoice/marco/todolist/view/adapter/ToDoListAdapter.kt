package com.bitchoice.marco.todolist.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.view.ToDoListApplication
import com.bitchoice.marco.todolist.view.adapter.viewholder.ToDoTaskViewHolder

class ToDoListAdapter(private val application: ToDoListApplication): RecyclerView.Adapter<ToDoTaskViewHolder>(), ListAccess<ToDoTask> {

    private var toDoList: MutableList<ToDoTask>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoTaskViewHolder {
        return ToDoTaskViewHolder.inflate(parent, application)
    }

    override fun onBindViewHolder(holder: ToDoTaskViewHolder, position: Int) {
        val task = toDoList!![position]
        holder.bind(task, this)
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
        if (toDoList != null) {
            toDoList!!.add(0, newTask)
            notifyItemInserted(0)
        }
    }

    override fun removeFromList(task: ToDoTask) {
        if (toDoList != null) {
            val position = toDoList!!.indexOf(task)
            toDoList!!.remove(task)
            notifyItemRemoved(position)
        }
    }

    override fun clearList() {
        if (toDoList != null) {
            toDoList!!.clear()
            notifyDataSetChanged()
        }
    }
}