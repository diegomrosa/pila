package com.diegomrosa.pila.apresentacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.diegomrosa.pila.modelo.Despesa;

import java.util.ArrayList;
import java.util.List;

public class DespesaAdapter extends ArrayAdapter<Despesa> {

    public DespesaAdapter(Context context) {
        this(context, new ArrayList<Despesa>());
    }

    public DespesaAdapter(Context context, List<Despesa> despesas) {
        super(context, android.R.layout.simple_list_item_2, despesas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(android.R.layout.simple_list_item_2, null);
        }
        Despesa despesa = getItem(position);
        TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        TextView text2 = (TextView) view.findViewById(android.R.id.text2);

        text1.setText(despesa.toString());
        text2.setText(despesa.getTags());
        return view;
    }

    public void setData(List<Despesa> despesas) {
        setNotifyOnChange(false);
        clear();
        for (Despesa despesa : despesas) {
            add(despesa);
        }
        notifyDataSetChanged();
    }
}
