package com.diegomrosa.pila.apresentacao;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.diegomrosa.pila.modelo.Dinheiro;
import com.diegomrosa.pila.R;
import com.diegomrosa.pila.modelo.Despesa;
import com.diegomrosa.pila.modelo.GerenciadorDespesas;

import java.util.Date;
import java.util.List;

public class DespesaActivity extends Activity {
    private EditText valorEdit;
    private EditText quantidadeEdit;
    private EditText totalEdit;
    private MultiAutoCompleteTextView tagsEdit;
    private Button salvarButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.despesa);
        valorEdit = (EditText) findViewById(R.id.valorEdit);
        quantidadeEdit = (EditText) findViewById(R.id.quantidadeEdit);
        totalEdit = (EditText) findViewById(R.id.totalEdit);
        tagsEdit = (MultiAutoCompleteTextView) findViewById(R.id.tagsEdit);
        salvarButton = (Button) findViewById(R.id.salvarButton);

        valorEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                calculaTotal();
            }
        });
        quantidadeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                calculaTotal();
            }
        });
        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date data = new Date();
                Dinheiro valor = getValor();
                double quantidade = getQuantidade();
                String tags = tagsEdit.getText().toString();
                Despesa despesa = new Despesa(null, data, valor, quantidade, tags);

                adicionaDespesa(despesa);
            }
        });
        tagsEdit.setAdapter(new TagAdapter(this));
        tagsEdit.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        carregaTags();
    }

    private void carregaTags() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return GerenciadorDespesas.getInstance().buscaTags();
            }

            @Override
            protected void onPostExecute(List<String> despesas) {
                ((TagAdapter) tagsEdit.getAdapter()).setData(despesas);
            }
        }.execute((Void[]) null);
    }

    private void adicionaDespesa(Despesa despesa) {
        new AsyncTask<Despesa, Void, Void>() {
            @Override
            protected Void doInBackground(Despesa... despesas) {
                GerenciadorDespesas.getInstance().adicionaDespesa(despesas[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                Toast.makeText(DespesaActivity.this, R.string.despesaSalvaMessage,
                        Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        }.execute(despesa);
    }

    private void calculaTotal() {
        Dinheiro total = getValor().multiplica(getQuantidade());

        totalEdit.setText(total.toString());
    }

    private Dinheiro getValor() {
        String valorStr = valorEdit.getText().toString();

        if ((valorStr == null) || (valorStr.trim().length() == 0)) {
            return Dinheiro.ZERO;
        }
        return Dinheiro.parse(valorStr);
    }

    private double getQuantidade() {
        String quantidadeStr = quantidadeEdit.getText().toString();

        if ((quantidadeStr == null) || (quantidadeStr.trim().length() == 0)) {
            return 0.0;
        }
        return Double.parseDouble(quantidadeStr);
    }
}
