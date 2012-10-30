package com.diegomrosa.pila.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.diegomrosa.pila.R;

public class MainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button newExpenseButton = (Button) findViewById(R.id.newExpenseButton);
        Button expensesListButton = (Button) findViewById(R.id.expensesListButton);
        Button aboutButton = (Button) findViewById(R.id.aboutButton);

        newExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
            }
        });
        expensesListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExpensesListActivity.class));
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
    }
}
