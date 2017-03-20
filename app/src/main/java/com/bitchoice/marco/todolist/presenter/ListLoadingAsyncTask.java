package com.bitchoice.marco.todolist.presenter;

import android.os.AsyncTask;

import com.bitchoice.marco.todolist.model.TableDAO;
import com.bitchoice.marco.todolist.model.ToDoList;

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */

public class ListLoadingAsyncTask extends AsyncTask<TableDAO, Void, ToDoList> {

    private ToDoListAdapter mAdapter;

    public ListLoadingAsyncTask(ToDoListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected ToDoList doInBackground(TableDAO... params) {
        params[0].onConfigurationChange(mAdapter.getActivityReference());
        return params[0].recoverNotes();
    }

    @Override
    protected void onPostExecute(ToDoList result) {
        super.onPostExecute(result);
        mAdapter.setToDoList(result);
        mAdapter.notifyDataSetChanged();
    }
}
