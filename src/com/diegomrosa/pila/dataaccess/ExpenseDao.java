package com.diegomrosa.pila.dataaccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diegomrosa.pila.dataaccess.Db.TableExpense;
import com.diegomrosa.pila.dataaccess.Db.TableExpense.Column;
import com.diegomrosa.pila.model.Expense;
import com.diegomrosa.pila.model.Money;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseDao {

    public void create(Expense expense) {
        SQLiteDatabase db = null;

        try {
            long newId = -1;

            db = new Db().getWritableDatabase();
            newId = db.insert(TableExpense.NAME, null, expenseToValues(expense));

            if (newId != -1) {
                expense.setId(newId);
            }
        } finally {
            Db.close(db);
        }
    }

    public List<Expense> read() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Expense> expenses = new ArrayList<Expense>();

        try {
            db = new Db().getReadableDatabase();
            cursor = db.query(TableExpense.NAME, Column.ALL,
                    null, null, null, null, TableExpense.ORDER_BY_DATA, null);

            while (cursor.moveToNext()) {
                expenses.add(cursorToExpense(cursor));
            }
        } finally {
            Db.close(cursor);
            Db.close(db);
        }
        return expenses;
    }

    public void delete(Long id) {
        SQLiteDatabase db = null;

        try {
            db = new Db().getWritableDatabase();
            db.delete(TableExpense.NAME, TableExpense.SQL_REMOCAO,
                    new String[] {id.toString()});
        } finally {
            Db.close(db);
        }
    }

    private ContentValues expenseToValues(Expense expense) {
        ContentValues values = new ContentValues();

        values.put(Column.DATE, expense.getDate().getTime());
        values.put(Column.VALUE, expense.getValue().longValue());
        values.put(Column.AMOUNT, expense.getAmount());
        values.put(Column.TAGS, expense.getTags());
        return values;
    }

    private Expense cursorToExpense(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(Column.ID));
        long date = cursor.getLong(cursor.getColumnIndex(Column.DATE));
        long value = cursor.getLong(cursor.getColumnIndex(Column.VALUE));
        double amount = cursor.getDouble(cursor.getColumnIndex(Column.AMOUNT));
        String tags = cursor.getString(cursor.getColumnIndex(Column.TAGS));

        return new Expense(id, new Date(date), new Money(value), amount, tags);
    }
}
