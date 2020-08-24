package com.bitchoice.marco.todolist.presenter

import android.app.Fragment
import android.os.Bundle
import java.util.*

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
@Suppress("UNCHECKED_CAST")
class RetainedFragment : Fragment() {

    private val mData = HashMap<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun put(key: String, `object`: Any) {
        mData[key] = `object`
    }

    operator fun <T> get(key: String?): T {
        return mData[key] as T
    }
}