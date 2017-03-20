package com.bitchoice.marco.todolist.model;

import java.util.ArrayList;

/**
 * Created by Marco Tulio Todeschini Coelho on 12/03/17
 * This android app source code is licenced under GNU GPLv3
 * and it's available at http://github.com/MarcoTTC/ToDoList
 * Visit my portfolio for more info at http://monolitonegro.wixsite.com/portfolio
 */

public class ToDoList {
    private ArrayList<Integer> ids;
    private ArrayList<String> notes;

    public ToDoList() {
        ids = new ArrayList<>();
        notes = new ArrayList<>();
    }

    public int getId(int pos) {
        return ids.get(pos);
    }

    public String getNote(int pos) {
        return notes.get(pos);
    }

    public int size() {
        return ids.size();
    }

    public void add(int id, String note) {
        ids.add(0, id);
        notes.add(0, note);
    }

    public void remove(int pos) {
        ids.remove(pos);
        notes.remove(pos);
    }

    public void clear() {
        ids.clear();
        notes.clear();
    }
}
