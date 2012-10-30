package com.diegomrosa.pila.dados;

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

    public static final class TabelaDespesa {
        public static final String NOME = "despesa";

        public static final class Coluna {
            public static final String ID = "despesa_id";
            public static final String DATA = "data";
            public static final String VALOR = "valor";
            public static final String QUANTIDADE = "quantidade";
            public static final String TAGS = "tags";
        }

        public static final String SQL_CRIACAO = String.format(
                "CREATE TABLE %s (\n" +
                "    %s INTEGER PRIMARY KEY AUTOINCREMENT,\n" +  // id
                "    %s INTEGER NOT NULL,\n" +  // data
                "    %s INTEGER NOT NULL,\n" +  // valor
                "    %s REAL NOT NULL,\n" +  // quantidade
                "    %s TEXT(200))\n",  // tags
                NOME,
                Coluna.ID,
                Coluna.DATA,
                Coluna.VALOR,
                Coluna.QUANTIDADE,
                Coluna.TAGS);
        public static final String ORDER_BY_DATA = Coluna.DATA + " DESC";
        public static final String SQL_REMOCAO = Coluna.ID + " = ?";
    }

    public static final class TabelaTag {
        public static final String NOME = "tag";

        public static final class Coluna {
            public static final String NOME = "nome";
        }

        public static final String SQL_CRIACAO = String.format(
                "CREATE TABLE %s (\n" +
                "    %s TEXT(50))\n",  // nome
                NOME,
                Coluna.NOME);
        public static final String ORDER_BY = Coluna.NOME + " ASC";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TabelaDespesa.SQL_CRIACAO);
        sqLiteDatabase.execSQL(TabelaTag.SQL_CRIACAO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)) {
            sqLiteDatabase.execSQL(TabelaTag.SQL_CRIACAO);
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
