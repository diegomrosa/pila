package com.diegomrosa.pila.dataaccess;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.diegomrosa.pila.PilaApp;

public class Db extends SQLiteOpenHelper {
    private static final String TAG = Db.class.getSimpleName();

    private static final String NAME = "com.diegomrosa.pila";
    private static final int VERSION = 2;

    public Db() {
        super(PilaApp.getContext(), NAME, null, VERSION);
    }

    public static final class TableExpense {
        public static final String NAME = "despesa";

        public static final class Column {
            public static final String ID = "despesa_id";
            public static final String DATE = "data";
            public static final String VALUE = "valor";
            public static final String AMOUNT = "quantidade";
            public static final String TAGS = "tags";

            public static final String[] ALL = {ID, DATE, VALUE, AMOUNT, TAGS};
        }

        public static final String CREATION_SQL = String.format(
                "CREATE TABLE %s (\n" +
                "    %s INTEGER PRIMARY KEY AUTOINCREMENT,\n" +  // id
                "    %s INTEGER NOT NULL,\n" +  // data
                "    %s INTEGER NOT NULL,\n" +  // valor
                "    %s REAL NOT NULL,\n" +  // quantidade
                "    %s TEXT(200))\n",  // tags
                NAME,
                Column.ID,
                Column.DATE,
                Column.VALUE,
                Column.AMOUNT,
                Column.TAGS);
        public static final String ORDER_BY_DATA = Column.DATE + " DESC";
        public static final String SQL_REMOCAO = Column.ID + " = ?";
    }

    public static final class TableTag {
        public static final String NAME = "tag";

        public static final class Column {
            public static final String NAME = "nome";
        }

        public static final String CREATION_SQL = String.format(
                "CREATE TABLE %s (\n" +
                "    %s TEXT(50))\n",  // nome
                NAME,
                Column.NAME);
        public static final String ORDER_BY = Column.NAME + " ASC";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TableExpense.CREATION_SQL);
        sqLiteDatabase.execSQL(TableTag.CREATION_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)) {
            sqLiteDatabase.execSQL(TableTag.CREATION_SQL);
        }
    }

    public static void close(SQLiteDatabase closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (Exception exc) {
                Log.w(TAG, "Error closing SQLite database.", exc);
            }
        }
    }

    public static void close(Cursor closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (Exception exc) {
                Log.w(TAG, "Error closing SQLite cursor.", exc);
            }
        }
    }
}
