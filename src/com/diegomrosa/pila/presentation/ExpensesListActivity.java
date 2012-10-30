package com.diegomrosa.pila.presentation;

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
import com.diegomrosa.pila.model.Expense;
import com.diegomrosa.pila.model.ExpensesManager;

import java.util.List;

public class ExpensesListActivity extends ListActivity {
    private static final int REQUEST_ADD = 1;

    private ExpenseAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_list);
        registerForContextMenu(getListView());
        setListAdapter(getAdapter());
        loadExpenses();
    }

    private ExpenseAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ExpenseAdapter(this);
        }
        return adapter;
    }

    private void loadExpenses() {
        new AsyncTask<Void, Void, List<Expense>>() {
            @Override
            protected List<Expense> doInBackground(Void... voids) {
                return ExpensesManager.getInstance().loadExpenses();
            }

            @Override
            protected void onPostExecute(List<Expense> expenses) {
                getAdapter().setData(expenses);
            }
        }.execute((Void[]) null);
    }

    private void removeExpense(Expense expense) {
        new AsyncTask<Expense, Void, List<Expense>>() {
            @Override
            protected List<Expense> doInBackground(Expense... expenses) {
                ExpensesManager.getInstance().removeExpense(expenses[0]);
                return ExpensesManager.getInstance().loadExpenses();
            }

            @Override
            protected void onPostExecute(List<Expense> expenses) {
                getAdapter().setData(expenses);
            }
        }.execute(expense);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expenses_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                startActivityForResult(new Intent(this, ExpenseActivity.class), REQUEST_ADD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        getMenuInflater().inflate(R.menu.expenses_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Expense expense = ((ExpenseAdapter) getListAdapter()).getItem(info.position);

        switch (item.getItemId()) {
            case R.id.removeItem:
                removeExpense(expense);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ADD:
                if (resultCode == Activity.RESULT_OK) {
                    loadExpenses();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
