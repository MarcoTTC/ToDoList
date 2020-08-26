package com.bitchoice.marco.todolist.model

import android.os.AsyncTask
import com.bitchoice.marco.todolist.model.room.ToDoTask
import com.bitchoice.marco.todolist.model.room.ToDoTaskDao
import com.bitchoice.marco.todolist.presenter.ListAccess
import com.bitchoice.marco.todolist.view.ToDoListApplication

class ToDoTaskManager(application: ToDoListApplication, val listAccess: ListAccess<ToDoTask>) {

    private var dao: ToDoTaskDao = application.database.getToDoTaskDao()

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
}