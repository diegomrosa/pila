package com.diegomrosa.pila.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.diegomrosa.pila.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    public ExpenseAdapter(Context context) {
        this(context, new ArrayList<Expense>());
    }

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, android.R.layout.simple_list_item_2, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(android.R.layout.simple_list_item_2, null);
        }
        Expense expense = getItem(position);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

        text1.setText(expense.toString());
        text2.setText(expense.getTags());
        return view;
    }

    public void setData(List<Expense> expenses) {
        setNotifyOnChange(false);
        clear();
        for (Expense expense : expenses) {
            add(expense);
        }
        notifyDataSetChanged();
    }
}
