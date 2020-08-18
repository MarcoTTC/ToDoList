package com.bitchoice.marco.todolist.presenter

import android.os.AsyncTask
import com.bitchoice.marco.todolist.model.TableDAO
import com.bitchoice.marco.todolist.model.ToDoList

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
class ListLoadingAsyncTask(private val mAdapter: ToDoListAdapter) : AsyncTask<TableDAO?, Void?, ToDoList>() {

    override fun doInBackground(vararg params: TableDAO?): ToDoList? {
        params[0]?.onConfigurationChange(mAdapter.activityReference)
        return params[0]?.recoverNotes()
    }

    override fun onPostExecute(result: ToDoList) {
        super.onPostExecute(result)
        mAdapter.setToDoList(result)
        mAdapter.notifyDataSetChanged()
    }

}