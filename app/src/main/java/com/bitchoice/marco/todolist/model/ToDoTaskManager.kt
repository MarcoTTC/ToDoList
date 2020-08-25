package com.bitchoice.marco.todolist.model

import android.os.AsyncTask
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.model.room.ToDoTaskDao
import com.bitchoice.marco.todolist.presenter.ListAccess
import com.bitchoice.marco.todolist.view.ToDoListApplication

class ToDoTaskManager(application: ToDoListApplication, val listAccess: ListAccess<ToDoTask>) {

    private var dao: ToDoTaskDao = application.database.getToDoTaskDao()

    fun recoverAllNotes() {
        object : AsyncTask<Unit, Unit, List<ToDoTask>>() {
            override fun doInBackground(vararg params: Unit?): List<ToDoTask> {
                return dao.getAll()
            }

            override fun onPostExecute(result: List<ToDoTask>) {
                super.onPostExecute(result)

                listAccess.setList(result)
            }
        }.execute()
    }

    fun save(value: String) {
        object : AsyncTask<Unit, Unit, ToDoTask>() {
            override fun doInBackground(vararg params: Unit?): ToDoTask {
                val newToDoTask = ToDoTask(0, value)

                dao.insertAll(newToDoTask)
                val correctUid = dao.getUidFromNote(value)
                newToDoTask.uid = correctUid

                return newToDoTask
            }

            override fun onPostExecute(newToDoTask: ToDoTask) {
                super.onPostExecute(newToDoTask)

                listAccess.addToList(newToDoTask)
            }
        }.execute()
    }

    fun delete(task: ToDoTask) {
        object : AsyncTask<Unit, Unit, ToDoTask>() {
            override fun doInBackground(vararg params: Unit?): ToDoTask {
                dao.delete(task)
                return task
            }

            override fun onPostExecute(result: ToDoTask) {
                super.onPostExecute(result)

                listAccess.removeFromList(result)
            }
        }.execute()
    }

    fun clear() {
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                dao.clear()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)

                listAccess.clearList()
            }
        }.execute()
    }

    companion object {
        const val NAME = "ToDoTaskManager"
    }
}