package com.diegomrosa.pila.presentation;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends ArrayAdapter<String> {

    public TagAdapter(Context context) {
        this(context, new ArrayList<String>());
    }

    public TagAdapter(Context context, List<String> tags) {
        super(context, android.R.layout.simple_dropdown_item_1line, tags);
    }

    public void setData(List<String> tags) {
        setNotifyOnChange(false);
        clear();
        for (String tag : tags) {
            add(tag);
        }
        notifyDataSetChanged();
    }
}
