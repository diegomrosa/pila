package com.diegomrosa.pila.model;

import com.diegomrosa.pila.dataaccess.ExpenseDao;
import com.diegomrosa.pila.dataaccess.TagsDao;

import java.util.*;

public class ExpensesManager {
    private static final String TAG_SEPARATOR = ",";

    private static ExpensesManager instance;

    private List<Expense> expensesList;
    private SortedSet<String> tagsList;

    private ExpensesManager() {
        expensesList = null;
        tagsList = null;
    }

    public static synchronized ExpensesManager getInstance() {
        if (instance == null) {
            instance = new ExpensesManager();
        }
        return instance;
    }

    public void addExpense(Expense expense) {
        String tags = expense.getTags();

        getExpensesList().add(0, expense);
        new ExpenseDao().create(expense);
        if ((tags != null) && (tags.trim().length() > 0)) {
            addTags(tags);
        }
    }

    private void addTags(String tags) {
        String[] tagArray = tags.split(TAG_SEPARATOR);
        TagsDao dao = new TagsDao();

        for (String rawTag : tagArray) {
            String tag = rawTag.trim();
            if ((tag.length() > 0) && !tagsList.contains(tag)) {
                tagsList.add(tag);
                dao.create(tag);
            }
        }
    }

    public void removeExpense(Expense expense) {
        getExpensesList().remove(expense);
        new ExpenseDao().delete(expense.getId());
    }

    public List<Expense> loadExpenses() {
        return new ArrayList<Expense>(getExpensesList());
    }

    public List<String> loadTags() {
        List<String> tags = new ArrayList<String>();

        for (String tag : getTagsList()) {
            tags.add(tag);
        }
        return tags;
    }

    private List<Expense> getExpensesList() {
        if (expensesList == null) {
            expensesList = new ExpenseDao().read();
        }
        return expensesList;
    }

    private SortedSet<String> getTagsList() {
        if (tagsList == null) {
            tagsList = new TagsDao().read();
        }
        return tagsList;
    }
}
