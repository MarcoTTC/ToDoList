package com.bitchoice.marco.todolist.model;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.lang.ref.WeakReference;

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */

public class TableDAO {
    private SQLiteDatabase database = null;
    private WeakReference<Activity> mActivity;

    public TableDAO() {
    }

    public void onConfigurationChange(WeakReference<Activity> activity) {
        mActivity = activity;
        initialize();
    }

    private void initialize() {
        try {
            if(mActivity != null) {
                database = mActivity.get().openOrCreateDatabase("appnotes", Context.MODE_PRIVATE, null);
                String sql_command = "CREATE TABLE IF NOT EXISTS notelist(id INTEGER PRIMARY KEY AUTOINCREMENT, note VARCHAR(140) ); ";
                database.execSQL(sql_command);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public ToDoList recoverNotes() {
        ToDoList list = new ToDoList();

        try {
            String sql_command = "SELECT * FROM notelist ORDER BY id ASC; ";
            Cursor cursor = database.rawQuery(sql_command, null);

            if(cursor!=null && cursor.getCount()>0) {
                cursor.moveToFirst();

                int cursorColumnId = cursor.getColumnIndex("id");
                int cursorColumnNote = cursor.getColumnIndex("note");

                boolean nextLine = true;
                while (nextLine) {
                    int currId = Integer.parseInt(cursor.getString(cursorColumnId));
                    String currNote = cursor.getString(cursorColumnNote);

                    list.add(currId, currNote);

                    nextLine = cursor.moveToNext();
                }

                cursor.close();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void closeDatabase() {
        if(database != null) {
            try {
                database.close();
                database = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean saveNote(String text) {
        if(database!=null) {
            try {
                String sql_command = "INSERT INTO notelist(note) VALUES('" + text + "'); ";
                database.execSQL(sql_command);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void removeNote(int id) {
        if(database != null) {
            try {
                String sql_command = "DELETE FROM notelist WHERE id=" + id + "; ";
                database.execSQL(sql_command);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearTable() {
        if(database != null) {
            try {
                String sql_command = "DELETE FROM notelist; ";
                database.execSQL(sql_command);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int recoverIdFromNote(String text) {
        if(database!=null) {
            try {
                int value;

                String sql_command = "SELECT id FROM notelist WHERE note='" + text + "'; ";
                Cursor cursor = database.rawQuery(sql_command, null);

                int cursorColumnId = cursor.getColumnIndex("id");
                cursor.moveToFirst();

                value = Integer.parseInt(cursor.getString(cursorColumnId));
                cursor.close();

                return value;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }
}
