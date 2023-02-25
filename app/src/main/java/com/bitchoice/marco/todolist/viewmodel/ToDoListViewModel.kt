package com.bitchoice.marco.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.model.room.ToDoTaskDao
import com.bitchoice.marco.todolist.view.ToDoListApplication
import kotlinx.coroutines.launch

/**
 * Created by Marco Tulio Todeschini Coelho on 26/08/20
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 */
class ToDoListViewModel(application: ToDoListApplication) : ViewModel() {

    val noteEditText: MutableLiveData<String?> = MutableLiveData()

    private val _updateWithList: MutableLiveData<List<ToDoTask>?> = MutableLiveData()
    val updateWithList: LiveData<List<ToDoTask>?>
        get() = _updateWithList

    private val _noteToAdd: MutableLiveData<ToDoTask?> = MutableLiveData()
    val noteToAdd: LiveData<ToDoTask?>
        get() = _noteToAdd

    private val _failedToSaveNote: MutableLiveData<Boolean> = MutableLiveData()
    val failedToSaveNote: LiveData<Boolean>
        get() = _failedToSaveNote

    private val _clearList: MutableLiveData<Boolean> = MutableLiveData()
    val clearList: LiveData<Boolean>
        get() = _clearList

    private var isSavingNote: Boolean = false

    private var dao: ToDoTaskDao = application.database.getToDoTaskDao()

    init {
        _updateWithList.value = null
        _noteToAdd.value = null
        _failedToSaveNote.value = false
    }

    fun recoverAllNotes() {
        viewModelScope.launch {
            val toDoList = dao.getAll()

            _updateWithList.value = toDoList
        }
    }

    fun saveNote() {
        if (!isSavingNote) {
            isSavingNote = true
            _noteToAdd.value = null
            _failedToSaveNote.value = false

            val value = noteEditText.value
            if (value?.isNotEmpty() == true) {
                viewModelScope.launch {
                    val newToDoTask = ToDoTask(0, value)

                    dao.insertAll(newToDoTask)
                    val correctUid = dao.getUidFromNote(value)
                    newToDoTask.uid = correctUid

                    _noteToAdd.value = newToDoTask
                    isSavingNote = false
                }
            } else {
                _failedToSaveNote.value = true
                isSavingNote = false
            }
        }
    }

    fun clearList() {
        _clearList.value = false

        viewModelScope.launch {
            dao.clear()

            _clearList.value = true
        }
    }
}