package com.diegomrosa.pila.dataaccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diegomrosa.pila.dataaccess.Db.TableTag;
import com.diegomrosa.pila.dataaccess.Db.TableTag.Column;

import java.util.SortedSet;
import java.util.TreeSet;

public class TagsDao {

    public void create(String tag) {
        SQLiteDatabase db = null;

        try {
            db = new Db().getWritableDatabase();
            db.insert(TableTag.NAME, null, tagToValues(tag));
        } finally {
            Db.close(db);
        }
    }

    public SortedSet<String> read() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SortedSet<String> tags = new TreeSet<String>();

        try {
            db = new Db().getReadableDatabase();
            cursor = db.query(TableTag.NAME,
                    new String[] {Column.NAME},
                    null, null, null, null, TableTag.ORDER_BY, null);

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

        values.put(Column.NAME, tag);
        return values;
    }

    private String cursorToTag(Cursor cursor) {
        String tag = cursor.getString(cursor.getColumnIndex(Column.NAME));

        return tag;
    }
}
