package com.bitchoice.marco.todolist.view.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitchoice.marco.todolist.R
import com.bitchoice.marco.todolist.databinding.ToDoTaskViewHolderBinding
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.presenter.ListAccess
import com.bitchoice.marco.todolist.view.ToDoListApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoTaskViewHolder(private val binding: ToDoTaskViewHolderBinding, private val application: ToDoListApplication) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: ToDoTask, listAccess: ListAccess<ToDoTask>) {
        val taskTitle = String.format(binding.root.resources.getString(R.string.note), task.uid)

        binding.taskTitle.text = taskTitle
        binding.taskNote.text = task.note

        val dao = application.database.getToDoTaskDao()
        binding.root.setOnLongClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                dao.delete(task)

                listAccess.removeFromList(task)
            }
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