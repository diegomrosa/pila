package com.diegomrosa.pila.apresentacao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.diegomrosa.pila.R;

public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() chamado.");
        setContentView(R.layout.main);
        Button novaDespesaButton = (Button) findViewById(R.id.novaDespesaButton);
        Button listaDespesasButton = (Button) findViewById(R.id.listaDespesasButton);
        Button sobreButton = (Button) findViewById(R.id.sobreButton);

        novaDespesaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DespesaActivity.class));
            }
        });
        listaDespesasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListaDespesasActivity.class));
            }
        });
        sobreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SobreActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() chamado.");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() chamado.");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() chamado.");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() chamado.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() chamado.");
    }
}
