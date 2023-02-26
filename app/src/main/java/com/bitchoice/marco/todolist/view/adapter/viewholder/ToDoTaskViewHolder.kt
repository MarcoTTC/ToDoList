package com.bitchoice.marco.todolist.view.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitchoice.marco.todolist.R
import com.bitchoice.marco.todolist.databinding.ToDoTaskViewHolderBinding
import com.bitchoice.marco.todolist.model.room.RoomDatabaseInstance
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.view.adapter.ListAccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 */
class ToDoTaskViewHolder(private val binding: ToDoTaskViewHolderBinding,
                         private val listAccess: ListAccess<ToDoTask>) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: ToDoTask) {
        val taskTitle = String.format(binding.root.resources.getString(R.string.note), task.uid)

        binding.taskTitle.text = taskTitle
        binding.taskNote.text = task.note

        val dao = RoomDatabaseInstance.getInstance(binding.root.context).getToDoTaskDao()
        binding.root.setOnLongClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                dao.delete(task)

                listAccess.removeFromList(task)
            }
            true
        }
    }

    companion object {
        fun inflate(parent: ViewGroup, listAccess: ListAccess<ToDoTask>): ToDoTaskViewHolder {
            val binding = ToDoTaskViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ToDoTaskViewHolder(binding, listAccess)
        }
    }
}