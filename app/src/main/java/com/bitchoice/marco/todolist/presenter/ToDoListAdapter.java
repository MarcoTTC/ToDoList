package com.bitchoice.marco.todolist.presenter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitchoice.marco.todolist.R;
import com.bitchoice.marco.todolist.model.TableDAO;
import com.bitchoice.marco.todolist.model.ToDoList;

import java.lang.ref.WeakReference;

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */

public class ToDoListAdapter extends BaseAdapter {

    private ToDoList list = null;

    private WeakReference<Activity> mActivity = null;
    private TableDAO table = null;

    public static final String NAME = "ToDoListAdapter";

    public ToDoListAdapter(Activity activity) {
        onConfigurationChange(activity);
    }

    public void onConfigurationChange(Activity activity) {
        mActivity = new WeakReference<>(activity);

        table = new TableDAO();
        new ListLoadingAsyncTask(this).execute(table);
    }

    public void onStop() {
        if(table != null) {
            table.closeDatabase();
            table = null;
            list = null;
        }
    }

    WeakReference<Activity> getActivityReference() {
        return mActivity;
    }

    void setToDoList(ToDoList toDoList) {
        list = toDoList;
    }

    public boolean save(String value) {
        if(list != null && table.saveNote(value)) {
            int noteId = table.recoverIdFromNote(value);

            list.add(noteId, value);
            notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void delete(int pos) {
        if(list != null) {
            int removeId = list.getId(pos);

            table.removeNote(removeId);
            list.remove(pos);

            notifyDataSetChanged();
        }
    }

    public void clear() {
        table.clearTable();
        list.clear();

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(list != null) {
            return list.getNote(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(list != null) {
            return (long) list.getId(position);
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int id_value = list.getId(position);
        final String text_value = list.getNote(position);
        final String note = mActivity.get().getString(R.string.note);

        RelativeLayout viewLayout;

        if(convertView == null) {
            LayoutInflater thisLayoutInflater = LayoutInflater.from(mActivity.get().getApplicationContext());
            viewLayout = (RelativeLayout) thisLayoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        } else {
            viewLayout = (RelativeLayout) convertView;
        }

        final TextView view_text1 = (TextView)viewLayout.findViewById(android.R.id.text1);
        final TextView view_text2 = (TextView)viewLayout.findViewById(android.R.id.text2);

        view_text1.setText(note + " " + id_value);
        view_text1.setTextColor(Color.BLACK);
        view_text2.setText(text_value);
        view_text2.setTextColor(Color.BLACK);

        return viewLayout;
    }
}
