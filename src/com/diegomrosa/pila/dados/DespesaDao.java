package com.diegomrosa.pila.dados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diegomrosa.pila.dados.Db.TabelaDespesa;
import com.diegomrosa.pila.dados.Db.TabelaDespesa.Coluna;
import com.diegomrosa.pila.modelo.Despesa;
import com.diegomrosa.pila.modelo.Dinheiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DespesaDao {

    public void cria(Despesa despesa) {
        SQLiteDatabase db = null;

        try {
            long newId = -1;

            db = new Db().getWritableDatabase();
            newId = db.insert(TabelaDespesa.NOME, null, despesaToValues(despesa));

            if (newId != -1) {
                despesa.setId(newId);
            }
        } finally {
            Db.close(db);
        }
    }

    public List<Despesa> busca() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Despesa> despesas = new ArrayList<Despesa>();

        try {
            db = new Db().getReadableDatabase();
            cursor = db.query(TabelaDespesa.NOME,
                    new String[] {Coluna.ID, Coluna.DATA, Coluna.VALOR, Coluna.QUANTIDADE, Coluna.TAGS},
                    null, null, null, null, TabelaDespesa.ORDER_BY_DATA, null);

            while (cursor.moveToNext()) {
                despesas.add(cursorToDespesa(cursor));
            }
        } finally {
            Db.close(cursor);
            Db.close(db);
        }
        return despesas;
    }

    public void remove(Long id) {
        SQLiteDatabase db = null;

        try {
            db = new Db().getWritableDatabase();
            db.delete(TabelaDespesa.NOME, Db.TabelaDespesa.SQL_REMOCAO,
                    new String[] {id.toString()});
        } finally {
            Db.close(db);
        }
    }

    private ContentValues despesaToValues(Despesa despesa) {
        ContentValues values = new ContentValues();

        values.put(Coluna.DATA, despesa.getData().getTime());
        values.put(Coluna.VALOR, despesa.getValor().longValue());
        values.put(Coluna.QUANTIDADE, despesa.getQuantidade());
        values.put(Coluna.TAGS, despesa.getTags());
        return values;
    }

    private Despesa cursorToDespesa(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(Coluna.ID));
        long data = cursor.getLong(cursor.getColumnIndex(Coluna.DATA));
        long valor = cursor.getLong(cursor.getColumnIndex(Coluna.VALOR));
        double quantidade = cursor.getDouble(cursor.getColumnIndex(Coluna.QUANTIDADE));
        String tags = cursor.getString(cursor.getColumnIndex(Coluna.TAGS));

        return new Despesa(id, new Date(data), new Dinheiro(valor), quantidade, tags);
    }
}
