package com.diegomrosa.pila.dados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diegomrosa.pila.dados.Db.TabelaTag;
import com.diegomrosa.pila.dados.Db.TabelaTag.Coluna;

import java.util.SortedSet;
import java.util.TreeSet;

public class TagsDao {

    public void cria(String tag) {
        SQLiteDatabase db = null;

        try {
            db = new Db().getWritableDatabase();
            db.insert(TabelaTag.NOME, null, tagToValues(tag));
        } finally {
            Db.close(db);
        }
    }

    public SortedSet<String> busca() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SortedSet<String> tags = new TreeSet<String>();

        try {
            db = new Db().getReadableDatabase();
            cursor = db.query(TabelaTag.NOME,
                    new String[] {Coluna.NOME},
                    null, null, null, null, TabelaTag.ORDER_BY, null);

            while (cursor.moveToNext()) {
                tags.add(cursorToTag(cursor));
            }
        } finally {
            Db.close(cursor);
            Db.close(db);
        }
        return tags;
    }

    private ContentValues tagToValues(String tag) {
        ContentValues values = new ContentValues();

        values.put(Coluna.NOME, tag);
        return values;
    }

    private String cursorToTag(Cursor cursor) {
        String tag = cursor.getString(cursor.getColumnIndex(Coluna.NOME));

        return tag;
    }
}
