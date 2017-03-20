package com.bitchoice.marco.todolist.presenter;

import android.app.Fragment;
import android.os.Bundle;

import java.util.HashMap;

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */

public class RetainedFragment extends Fragment {
    private HashMap<String, Object> mData = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    public void put(String key, Object object) {
        mData.put(key, object);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) mData.get(key);
    }
}
