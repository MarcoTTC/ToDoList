package com.bitchoice.marco.todolist.presenter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import com.bitchoice.marco.todolist.R
import com.bitchoice.marco.todolist.model.TableDAO
import com.bitchoice.marco.todolist.model.ToDoList
import java.lang.ref.WeakReference

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
class ToDoListAdapter(activity: Activity) : BaseAdapter() {

    private var list: ToDoList? = null
    var activityReference: WeakReference<Activity>? = null
        private set
    private var table: TableDAO? = null

    fun onConfigurationChange(activity: Activity) {
        activityReference = WeakReference(activity)
        table = TableDAO()
        ListLoadingAsyncTask(this).execute(table)
    }

    fun onStop() {
        if (table != null) {
            table!!.closeDatabase()
            table = null
            list = null
        }
    }

    fun setToDoList(toDoList: ToDoList?) {
        list = toDoList
    }

    fun save(value: String): Boolean {
        return if (list != null && table!!.saveNote(value)) {
            val noteId = table!!.recoverIdFromNote(value)
            list!!.add(noteId, value)
            notifyDataSetChanged()
            true
        } else {
            false
        }
    }

    fun delete(pos: Int) {
        if (list != null) {
            val removeId = list!!.getId(pos)
            table!!.removeNote(removeId)
            list!!.remove(pos)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        table!!.clearTable()
        list!!.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return if (list != null) {
            list!!.size()
        } else {
            0
        }
    }

    override fun getItem(position: Int): Any? {
        return if (list != null) {
            list!!.getNote(position)
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long {
        return if (list != null) {
            list!!.getId(position).toLong()
        } else {
            0
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val idValue = list!!.getId(position)
        val textValue = list!!.getNote(position)
        val note = activityReference!!.get()!!.getString(R.string.note)
        val viewLayout: RelativeLayout
        viewLayout = if (convertView == null) {
            val thisLayoutInflater = LayoutInflater.from(activityReference!!.get()!!.applicationContext)
            thisLayoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false) as RelativeLayout
        } else {
            convertView as RelativeLayout
        }

        val viewText1 = viewLayout.findViewById<View>(android.R.id.text1) as TextView
        val viewText2 = viewLayout.findViewById<View>(android.R.id.text2) as TextView
        viewText1.text = "$note $idValue"
        viewText1.setTextColor(Color.BLACK)
        viewText2.text = textValue
        viewText2.setTextColor(Color.BLACK)

        return viewLayout
    }

    companion object {
        const val NAME = "ToDoListAdapter"
    }

    init {
        onConfigurationChange(activity)
    }
}