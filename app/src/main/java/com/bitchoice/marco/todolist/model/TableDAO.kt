package com.bitchoice.marco.todolist.model

import android.app.Activity
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.lang.ref.WeakReference

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
class TableDAO {

    private var database: SQLiteDatabase? = null
    private var mActivity: WeakReference<Activity>? = null

    fun onConfigurationChange(activity: WeakReference<Activity>?) {
        mActivity = activity
        initialize()
    }

    private fun initialize() {
        try {
            if (mActivity != null) {
                database = mActivity!!.get()!!.openOrCreateDatabase("appnotes", Context.MODE_PRIVATE, null)
                val sqlCommand = "CREATE TABLE IF NOT EXISTS notelist(id INTEGER PRIMARY KEY AUTOINCREMENT, note VARCHAR(140) ); "
                database?.execSQL(sqlCommand)
            }
        } catch (e: SQLException) {
            Log.e(TableDAO::class.java.canonicalName, e.message, e)
            e.printStackTrace()
        }
    }

    fun recoverNotes(): ToDoList {
        val list = ToDoList()
        try {
            val sqlCommand = "SELECT * FROM notelist ORDER BY id ASC; "
            val cursor = database!!.rawQuery(sqlCommand, null)
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val cursorColumnId = cursor.getColumnIndex("id")
                val cursorColumnNote = cursor.getColumnIndex("note")
                var nextLine = true
                while (nextLine) {
                    val currId = cursor.getString(cursorColumnId).toInt()
                    val currNote = cursor.getString(cursorColumnNote)
                    list.add(currId, currNote)
                    nextLine = cursor.moveToNext()
                }
                cursor.close()
            }
        } catch (e: SQLException) {
            Log.e(TableDAO::class.java.canonicalName, e.message, e)
            e.printStackTrace()
        }
        return list
    }

    fun closeDatabase() {
        if (database != null) {
            try {
                database!!.close()
                database = null
            } catch (e: SQLException) {
                Log.e(TableDAO::class.java.canonicalName, e.message, e)
                e.printStackTrace()
            }
        }
    }

    fun saveNote(text: String): Boolean {
        if (database != null) {
            try {
                val sqlCommand = "INSERT INTO notelist(note) VALUES('$text'); "
                database!!.execSQL(sqlCommand)
                return true
            } catch (e: SQLException) {
                Log.e(TableDAO::class.java.canonicalName, e.message, e)
                e.printStackTrace()
            }
        }
        return false
    }

    fun removeNote(id: Int) {
        if (database != null) {
            try {
                val sqlCommand = "DELETE FROM notelist WHERE id=$id; "
                database!!.execSQL(sqlCommand)
            } catch (e: SQLException) {
                Log.e(TableDAO::class.java.canonicalName, e.message, e)
                e.printStackTrace()
            }
        }
    }

    fun clearTable() {
        if (database != null) {
            try {
                val sqlCommand = "DELETE FROM notelist; "
                database!!.execSQL(sqlCommand)
            } catch (e: SQLException) {
                Log.e(TableDAO::class.java.canonicalName, e.message, e)
                e.printStackTrace()
            }
        }
    }

    fun recoverIdFromNote(text: String): Int {
        if (database != null) {
            try {
                val value: Int
                val sqlCommand = "SELECT id FROM notelist WHERE note='$text'; "
                val cursor = database!!.rawQuery(sqlCommand, null)
                val cursorColumnId = cursor.getColumnIndex("id")
                cursor.moveToFirst()
                value = cursor.getString(cursorColumnId).toInt()
                cursor.close()
                return value
            } catch (e: SQLException) {
                Log.e(TableDAO::class.java.canonicalName, e.message, e)
                e.printStackTrace()
            }
        }
        return 0
    }
}