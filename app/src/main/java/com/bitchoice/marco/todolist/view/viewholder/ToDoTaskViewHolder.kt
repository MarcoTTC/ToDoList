package com.bitchoice.marco.todolist.view.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitchoice.marco.todolist.R
import com.bitchoice.marco.todolist.databinding.ToDoTaskViewHolderBinding
import com.bitchoice.marco.todolist.model.ToDoTaskManager
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.presenter.ToDoTaskListAccess
import com.bitchoice.marco.todolist.view.ToDoListApplication

class ToDoTaskViewHolder(private val binding: ToDoTaskViewHolderBinding, private val application: ToDoListApplication) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: ToDoTask, listAccess: ToDoTaskListAccess) {
        val taskTitle = String.format(binding.root.resources.getString(R.string.note), task.uid)

        binding.taskTitle.text = taskTitle
        binding.taskNote.text = task.note

        val manager = ToDoTaskManager(application, listAccess)
        binding.root.setOnLongClickListener {
            manager.delete(task)
            true
        }
    }

    companion object {
        fun inflate(parent: ViewGroup, application: ToDoListApplication): ToDoTaskViewHolder {
            val binding = ToDoTaskViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ToDoTaskViewHolder(binding, application)
        }
    }
}