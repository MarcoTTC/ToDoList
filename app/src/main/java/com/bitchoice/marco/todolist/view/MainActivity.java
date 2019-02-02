package com.bitchoice.marco.todolist.view;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bitchoice.marco.todolist.R;
import com.bitchoice.marco.todolist.presenter.RetainedFragment;
import com.bitchoice.marco.todolist.presenter.ToDoListAdapter;

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */

public class MainActivity extends AppCompatActivity {

    private EditText note_editText;
    private ListView note_listView;

    private AlertDialog.Builder eraseDialog;
    private AlertDialog.Builder aboutDialog;
    private AlertDialog.Builder creditsDialog;

    private ToDoListAdapter toDoListAdapter;
    private RetainedFragment mRetainedFragment = null;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        note_editText = (EditText) findViewById(R.id.editText_id);
        Button button_add = (Button) findViewById(R.id.button_add_id);
        note_listView = (ListView) findViewById(R.id.listView_id);

        FragmentManager manager = getFragmentManager();
        mRetainedFragment = (RetainedFragment) manager.findFragmentByTag(TAG);
        if(mRetainedFragment == null) {
            mRetainedFragment = new RetainedFragment();
            manager.beginTransaction().add(mRetainedFragment, TAG).commit();

            initialize();
        } else {
            reinitialize();
        }

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typedText = note_editText.getText().toString();

                if(!typedText.isEmpty()) {
                    if(toDoListAdapter.save(typedText)) {
                        String messageSaved = getString(R.string.note_saved);
                        Toast.makeText(MainActivity.this, messageSaved, Toast.LENGTH_SHORT).show();
                        note_editText.setText("");
                    }
                } else {
                    String messageEmpty = getString(R.string.note_empty);
                    Toast.makeText(MainActivity.this, messageEmpty, Toast.LENGTH_SHORT).show();
                }
            }
        });

        note_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoListAdapter.delete(position);
                return true;
            }
        });

        note_listView.setLongClickable(true);

        eraseDialog = new AlertDialog.Builder(this);
        eraseDialog.setTitle(R.string.erase_title);
        eraseDialog.setCancelable(true);
        eraseDialog.setMessage(R.string.erase_confirm);
        eraseDialog.setPositiveButton(R.string.button_erase, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDoListAdapter.clear();
                dialog.dismiss();
            }
        });
        eraseDialog.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        eraseDialog.create();

        String aboutMessage;
        try {
            aboutMessage = getString(R.string.app_name) + "\n" + getString(R.string.about_version) + getPackageManager().getPackageInfo(getPackageName(), 0).versionName + "\n" + getString(R.string.about_message);
        } catch(PackageManager.NameNotFoundException e) {
            aboutMessage = getString(R.string.app_name) + "\n" + getString(R.string.about_message);
        }

        aboutDialog = new AlertDialog.Builder(this);
        aboutDialog.setTitle(R.string.about_title);
        aboutDialog.setCancelable(true);
        aboutDialog.setMessage(aboutMessage);
        aboutDialog.setPositiveButton(R.string.button_visit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String portfolio_url = getString(R.string.portfolio_url);
                Intent visitPortfolio = new Intent(Intent.ACTION_VIEW);
                visitPortfolio.setData(Uri.parse(portfolio_url));
                if(visitPortfolio.resolveActivity(getPackageManager()) != null) {
                    startActivity(visitPortfolio);
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_browser_available, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        aboutDialog.setNegativeButton(R.string.button_notnow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        aboutDialog.create();

        creditsDialog = new AlertDialog.Builder(this);
        creditsDialog.setTitle(R.string.credits_title);
        creditsDialog.setCancelable(true);
        creditsDialog.setMessage(R.string.credits_message);
        creditsDialog.setPositiveButton(R.string.button_visit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String credits_url = getString(R.string.credits_url);
                Intent visitCredits = new Intent(Intent.ACTION_VIEW);
                visitCredits.setData(Uri.parse(credits_url));
                if(visitCredits.resolveActivity(getPackageManager()) != null) {
                    startActivity(visitCredits);
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_browser_available, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        creditsDialog.setNegativeButton(R.string.button_notnow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        creditsDialog.create();
    }

    private void initialize() {
        toDoListAdapter = new ToDoListAdapter(this);
        mRetainedFragment.put(ToDoListAdapter.NAME, toDoListAdapter);

        note_listView.setAdapter(toDoListAdapter);
    }

    private void reinitialize() {
        toDoListAdapter = mRetainedFragment.get(ToDoListAdapter.NAME);

        if (toDoListAdapter == null) {
            initialize();
        } else {
            toDoListAdapter.onConfigurationChange(this);
            note_listView.setAdapter(toDoListAdapter);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        toDoListAdapter.onConfigurationChange(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        toDoListAdapter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_option) {
            eraseDialog.show();
            return true;
        } else if(id == R.id.about_option) {
            aboutDialog.show();
            return true;
        } else if(id == R.id.credits_option) {
            creditsDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
