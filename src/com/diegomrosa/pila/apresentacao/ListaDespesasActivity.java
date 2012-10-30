package com.diegomrosa.pila.apresentacao;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import com.diegomrosa.pila.R;
import com.diegomrosa.pila.modelo.Despesa;
import com.diegomrosa.pila.modelo.GerenciadorDespesas;

import java.util.List;

public class ListaDespesasActivity extends ListActivity {
    private static final int ADICIONA_REQUEST = 1;

    private DespesaAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_despesas);
        registerForContextMenu(getListView());
        setListAdapter(getAdapter());
        carregaDespesas();
    }

    private DespesaAdapter getAdapter() {
        if (adapter == null) {
            adapter = new DespesaAdapter(this);
        }
        return adapter;
    }

    private void carregaDespesas() {
        new AsyncTask<Void, Void, List<Despesa>>() {
            @Override
            protected List<Despesa> doInBackground(Void... voids) {
                return GerenciadorDespesas.getInstance().buscaDespesas();
            }

            @Override
            protected void onPostExecute(List<Despesa> despesas) {
                getAdapter().setData(despesas);
            }
        }.execute((Void[]) null);
    }

    private void removeDespesa(Despesa despesa) {
        new AsyncTask<Despesa, Void, List<Despesa>>() {
            @Override
            protected List<Despesa> doInBackground(Despesa... despesas) {
                GerenciadorDespesas.getInstance().removeDespesa(despesas[0]);
                return GerenciadorDespesas.getInstance().buscaDespesas();
            }

            @Override
            protected void onPostExecute(List<Despesa> despesas) {
                getAdapter().setData(despesas);
            }
        }.execute(despesa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_despesas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adicionaItem:
                startActivityForResult(new Intent(this, DespesaActivity.class), ADICIONA_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        getMenuInflater().inflate(R.menu.lista_despesas_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Despesa despesa = ((DespesaAdapter) getListAdapter()).getItem(info.position);

        switch (item.getItemId()) {
            case R.id.removeItem:
                removeDespesa(despesa);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADICIONA_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    carregaDespesas();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
