package com.diegomrosa.pila.presentation;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.diegomrosa.pila.model.Money;
import com.diegomrosa.pila.R;
import com.diegomrosa.pila.model.Expense;
import com.diegomrosa.pila.model.ExpensesManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

public class ExpenseActivity extends Activity {
    private static final double DEFAULT_AMOUNT = 1.0;
    private static final NumberFormat AMOUNT_FORMAT = NumberFormat.getNumberInstance();

    private EditText valueEdit;
    private EditText amountEdit;
    private EditText totalEdit;
    private MultiAutoCompleteTextView tagsEdit;
    private Button saveButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense);
        valueEdit = (EditText) findViewById(R.id.valueEdit);
        amountEdit = (EditText) findViewById(R.id.amountEdit);
        totalEdit = (EditText) findViewById(R.id.totalEdit);
        tagsEdit = (MultiAutoCompleteTextView) findViewById(R.id.tagsEdit);
        saveButton = (Button) findViewById(R.id.saveButton);

        amountEdit.setText(AMOUNT_FORMAT.format(DEFAULT_AMOUNT));
        valueEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                calculateTotal();
            }
        });
        amountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                calculateTotal();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                Money value = getValue();
                double amount = getAmount();
                String tags = tagsEdit.getText().toString();
                Expense expense = new Expense(null, date, value, amount, tags);

                addExpense(expense);
            }
        });
        tagsEdit.setAdapter(new TagAdapter(this));
        tagsEdit.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        loadTags();
    }

    private void loadTags() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return ExpensesManager.getInstance().loadTags();
            }

            @Override
            protected void onPostExecute(List<String> despesas) {
                ((TagAdapter) tagsEdit.getAdapter()).setData(despesas);
            }
        }.execute((Void[]) null);
    }

    private void addExpense(Expense expense) {
        new AsyncTask<Expense, Void, Void>() {
            @Override
            protected Void doInBackground(Expense... expenses) {
                ExpensesManager.getInstance().addExpense(expenses[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                Toast.makeText(ExpenseActivity.this, R.string.expenseSavedMessage,
                        Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        }.execute(expense);
    }

    private void calculateTotal() {
        Money total = getValue().multiply(getAmount());

        totalEdit.setText(total.toString());
    }

    private Money getValue() {
        String valueStr = valueEdit.getText().toString();

        if ((valueStr == null) || (valueStr.trim().length() == 0)) {
            return Money.ZERO;
        }
        return Money.parse(valueStr);
    }

    private double getAmount() {
        String amountStr = amountEdit.getText().toString();

        if ((amountStr == null) || (amountStr.trim().length() == 0)) {
            return 0.0;
        }
        return Double.parseDouble(amountStr);
    }
}
