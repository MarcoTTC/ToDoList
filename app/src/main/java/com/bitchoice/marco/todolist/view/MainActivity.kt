package com.bitchoice.marco.todolist.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bitchoice.marco.todolist.R
import com.bitchoice.marco.todolist.databinding.ActivityMainBinding
import com.bitchoice.marco.todolist.model.ToDoTaskManager
import com.bitchoice.marco.todolist.model.room.ToDoListDatabase
import com.bitchoice.marco.todolist.presenter.RetainedFragment
import com.bitchoice.marco.todolist.presenter.ToDoListAdapter

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var eraseDialog: AlertDialog.Builder? = null
    private var aboutDialog: AlertDialog.Builder? = null
    private var creditsDialog: AlertDialog.Builder? = null
    private var mRetainedFragment: RetainedFragment? = null

    private lateinit var database: ToDoListDatabase
    private lateinit var toDoListAdapter: ToDoListAdapter
    private lateinit var toDoTaskManager: ToDoTaskManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val manager = fragmentManager
        mRetainedFragment = manager.findFragmentByTag(TAG) as RetainedFragment?
        if (mRetainedFragment == null) {
            mRetainedFragment = RetainedFragment()
            manager.beginTransaction().add(mRetainedFragment, TAG).commit()
            initialize()
        } else {
            reinitialize()
        }

        toDoTaskManager.recoverAllNotes()

        binding!!.addBtn.setOnClickListener {
            val typedText = binding!!.addInput.text.toString()
            if (typedText.isNotEmpty()) {
                toDoTaskManager.save(typedText)
                val messageSaved = getString(R.string.note_saved)
                Toast.makeText(this@MainActivity, messageSaved, Toast.LENGTH_SHORT).show()
                binding!!.addInput.setText("")
            } else {
                val messageEmpty = getString(R.string.note_empty)
                Toast.makeText(this@MainActivity, messageEmpty, Toast.LENGTH_SHORT).show()
            }
        }

        binding!!.listView.onItemLongClickListener = OnItemLongClickListener { _, _, position, _ ->
            toDoTaskManager.delete(position)
            true
        }

        binding!!.listView.isLongClickable = true
        eraseDialog = AlertDialog.Builder(this)
        eraseDialog!!.setTitle(R.string.erase_title)
        eraseDialog!!.setCancelable(true)
        eraseDialog!!.setMessage(R.string.erase_confirm)
        eraseDialog!!.setPositiveButton(R.string.button_erase) { dialog, _ ->
            toDoTaskManager.clear()
            dialog.dismiss()
        }

        eraseDialog!!.setNegativeButton(R.string.button_cancel) { dialog, _ ->
            dialog.cancel()
        }
        eraseDialog!!.create()
        val aboutMessage: String = try {
            """
            ${getString(R.string.app_name)}
            ${getString(R.string.about_version)}${packageManager.getPackageInfo(packageName, 0).versionName}
            ${getString(R.string.about_message)}
            """.trimIndent()
        } catch (e: PackageManager.NameNotFoundException) {
            """
            ${getString(R.string.app_name)}
            ${getString(R.string.about_message)}
            """.trimIndent()
        }

        aboutDialog = AlertDialog.Builder(this)
        aboutDialog!!.setTitle(R.string.about_title)
        aboutDialog!!.setCancelable(true)
        aboutDialog!!.setMessage(aboutMessage)
        aboutDialog!!.setPositiveButton(R.string.button_visit) { dialog, _ ->
            val portfolioUrl = getString(R.string.portfolio_url)
            val visitPortfolio = Intent(Intent.ACTION_VIEW)
            visitPortfolio.data = Uri.parse(portfolioUrl)
            if (visitPortfolio.resolveActivity(packageManager) != null) {
                startActivity(visitPortfolio)
            } else {
                Toast.makeText(this@MainActivity, R.string.no_browser_available, Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        aboutDialog!!.setNegativeButton(R.string.button_notnow) { dialog, _ ->
            dialog.cancel()
        }
        aboutDialog!!.create()

        creditsDialog = AlertDialog.Builder(this)
        creditsDialog!!.setTitle(R.string.credits_title)
        creditsDialog!!.setCancelable(true)
        creditsDialog!!.setMessage(R.string.credits_message)
        creditsDialog!!.setPositiveButton(R.string.button_visit) { dialog, _ ->
            val creditsUrl = getString(R.string.credits_url)
            val visitCredits = Intent(Intent.ACTION_VIEW)
            visitCredits.data = Uri.parse(creditsUrl)
            if (visitCredits.resolveActivity(packageManager) != null) {
                startActivity(visitCredits)
            } else {
                Toast.makeText(this@MainActivity, R.string.no_browser_available, Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        creditsDialog!!.setNegativeButton(R.string.button_notnow) { dialog, _ ->
            dialog.cancel()
        }
        creditsDialog!!.create()
    }

    private fun initialize() {
        database = Room.databaseBuilder(
                applicationContext,
                ToDoListDatabase::class.java,
          "ToDoList-Database"
        ).build()

        toDoListAdapter = ToDoListAdapter(this)
        toDoTaskManager = ToDoTaskManager(database, toDoListAdapter)

        mRetainedFragment!!.put("database", database)
        mRetainedFragment!!.put(ToDoListAdapter.NAME, toDoListAdapter)
        mRetainedFragment!!.put("ToDoTaskManager", toDoTaskManager)
        binding!!.listView.adapter = toDoListAdapter
    }

    private fun reinitialize() {
        database = mRetainedFragment!!["database"]
        toDoListAdapter = mRetainedFragment!![ToDoListAdapter.NAME]
        toDoTaskManager = mRetainedFragment!![ToDoTaskManager.NAME]

        toDoListAdapter.onConfigurationChange(this)
        binding!!.listView.adapter = toDoListAdapter
    }

    override fun onRestart() {
        super.onRestart()
        toDoListAdapter.onConfigurationChange(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_todo_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_option -> {
                eraseDialog!!.show()
                true
            }
            R.id.about_option -> {
                aboutDialog!!.show()
                true
            }
            R.id.credits_option -> {
                creditsDialog!!.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}