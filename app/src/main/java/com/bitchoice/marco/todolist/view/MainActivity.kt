package com.bitchoice.marco.todolist.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bitchoice.marco.todolist.R
import com.bitchoice.marco.todolist.databinding.ActivityMainBinding
import com.bitchoice.marco.todolist.model.ToDoTaskManager
import com.bitchoice.marco.todolist.view.adapter.ToDoListAdapter
import com.bitchoice.marco.todolist.viewmodel.ToDoListViewModel
import com.bitchoice.marco.todolist.viewmodel.factory.ViewModelWithApplicationFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */
class MainActivity : AppCompatActivity() {

    private lateinit var application: ToDoListApplication

    private lateinit var binding: ActivityMainBinding
    private lateinit var toDoListViewModel: ToDoListViewModel

    private lateinit var eraseDialog: MaterialAlertDialogBuilder
    private lateinit var aboutDialog: MaterialAlertDialogBuilder
    private lateinit var creditsDialog: MaterialAlertDialogBuilder

    private lateinit var toDoTaskListAdapter: ToDoListAdapter
    private lateinit var toDoTaskManager: ToDoTaskManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        application = getApplication() as ToDoListApplication

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelWithApplicationFactory = ViewModelWithApplicationFactory(application)
        toDoListViewModel = ViewModelProvider(this, viewModelWithApplicationFactory).get(ToDoListViewModel::class.java)

        toDoListViewModel.noteToAdd.observe(this, { task ->
            if (task != null) {
                toDoTaskListAdapter.addToList(task)

                val messageSaved = getString(R.string.note_saved)
                Toast.makeText(this@MainActivity, messageSaved, Toast.LENGTH_SHORT).show()

                binding.addInput.setText("")
            }
        })

        toDoListViewModel.failedToSaveNote.observe(this, { success ->
            if (success) {
                val messageSaved = getString(R.string.note_empty)
                Toast.makeText(this@MainActivity, messageSaved, Toast.LENGTH_SHORT).show()
            }
        })

        binding.viewModel = toDoListViewModel

        toDoTaskListAdapter = ToDoListAdapter(application)
        toDoTaskManager = ToDoTaskManager(application, toDoTaskListAdapter)

        binding.recyclerView.adapter = toDoTaskListAdapter

        toDoTaskManager.recoverAllNotes()

        binding.addBtn.setOnClickListener {
            toDoListViewModel.saveNote()
        }

        binding.recyclerView.isLongClickable = true
        eraseDialog = MaterialAlertDialogBuilder(this)
        eraseDialog.setTitle(R.string.erase_title)
        eraseDialog.setCancelable(true)
        eraseDialog.setMessage(R.string.erase_confirm)
        eraseDialog.setPositiveButton(R.string.button_erase) { dialog, _ ->
            toDoTaskManager.clear()
            dialog.dismiss()
        }

        eraseDialog.setNegativeButton(R.string.button_cancel) { dialog, _ ->
            dialog.cancel()
        }
        eraseDialog.create()
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

        aboutDialog = MaterialAlertDialogBuilder(this)
        aboutDialog.setTitle(R.string.about_title)
        aboutDialog.setCancelable(true)
        aboutDialog.setMessage(aboutMessage)
        aboutDialog.setPositiveButton(R.string.button_visit) { dialog, _ ->
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
        aboutDialog.setNegativeButton(R.string.button_notnow) { dialog, _ ->
            dialog.cancel()
        }
        aboutDialog.create()

        creditsDialog = MaterialAlertDialogBuilder(this)
        creditsDialog.setTitle(R.string.credits_title)
        creditsDialog.setCancelable(true)
        creditsDialog.setMessage(R.string.credits_message)
        creditsDialog.setPositiveButton(R.string.button_visit) { dialog, _ ->
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
        creditsDialog.setNegativeButton(R.string.button_notnow) { dialog, _ ->
            dialog.cancel()
        }
        creditsDialog.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        application.database.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_todo_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_option -> {
                eraseDialog.show()
                true
            }
            R.id.about_option -> {
                aboutDialog.show()
                true
            }
            R.id.credits_option -> {
                creditsDialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}