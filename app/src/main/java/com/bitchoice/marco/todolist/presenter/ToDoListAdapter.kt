package com.bitchoice.marco.todolist.presenter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import com.bitchoice.marco.todolist.R
import com.bitchoice.marco.todolist.model.room.ToDoTask
import java.lang.ref.WeakReference

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
class ToDoListAdapter(context: Context) : BaseAdapter(), ToDoTaskListAccess {

    private lateinit var contextReference: WeakReference<Context>

    private var list: MutableList<ToDoTask>? = null

    init {
        onConfigurationChange(context)
    }

    fun onConfigurationChange(context: Context) {
        contextReference = WeakReference(context)
    }

    override fun setToDoTaskList(toDoTaskList: List<ToDoTask>) {
        list = toDoTaskList.toMutableList()
    }

    override fun addTask(newTask: ToDoTask) {
        if (list != null) {
            list!!.add(0, newTask)
            notifyDataSetChanged()
        }
    }

    override fun removeTask(task: ToDoTask) {
        if (list != null) {
            list!!.remove(task)
            notifyDataSetChanged()
        }
    }

    override fun clearList() {
        if (list != null) {
            list!!.clear()
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int {
        return if (list == null) {
            0
        } else {
            list!!.size
        }
    }

    override fun getItem(position: Int): Any? {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return list!![position].uid.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val idValue = list!![position].uid
        val textValue = list!![position].note
        val note = contextReference.get()?.resources?.getString(R.string.note)
        val viewLayout: RelativeLayout
        viewLayout = if (convertView == null) {
            val thisLayoutInflater = LayoutInflater.from(contextReference.get())
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
}