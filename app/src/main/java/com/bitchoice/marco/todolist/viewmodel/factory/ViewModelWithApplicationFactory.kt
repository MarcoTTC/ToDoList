package com.bitchoice.marco.todolist.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bitchoice.marco.todolist.view.ToDoListApplication

class ViewModelWithApplicationFactory(private val application: ToDoListApplication): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance(application)
    }
}