package com.diegomrosa.pila.modelo;

import com.diegomrosa.pila.dados.DespesaDao;
import com.diegomrosa.pila.dados.TagsDao;

import java.util.*;

public class GerenciadorDespesas {
    private static final String TAG_SEPARATOR = ",";

    private static GerenciadorDespesas instance;

    private List<Despesa> listaDespesas;
    private SortedSet<String> listaTags;

    private GerenciadorDespesas() {
        listaDespesas = null;
        listaTags = null;
    }

    public static synchronized GerenciadorDespesas getInstance() {
        if (instance == null) {
            instance = new GerenciadorDespesas();
        }
        return instance;
    }

    public void adicionaDespesa(Despesa despesa) {
        String tags = despesa.getTags();

        getListaDespesas().add(0, despesa);
        new DespesaDao().cria(despesa);
        if ((tags != null) && (tags.trim().length() > 0)) {
            adicionaTags(tags);
        }
    }

    private void adicionaTags(String tags) {
        String[] tagArray = tags.split(TAG_SEPARATOR);
        TagsDao dao = new TagsDao();

        for (String rawTag : tagArray) {
            String tag = rawTag.trim();
            if ((tag.length() > 0) && !listaTags.contains(tag)) {
                listaTags.add(tag);
                dao.cria(tag);
            }
        }
    }

    public void removeDespesa(Despesa despesa) {
        getListaDespesas().remove(despesa);
        new DespesaDao().remove(despesa.getId());
    }

    public List<Despesa> buscaDespesas() {
        return new ArrayList<Despesa>(getListaDespesas());
    }

    public List<String> buscaTags() {
        List<String> tags = new ArrayList<String>();

        for (String tag : getListaTags()) {
            tags.add(tag);
        }
        return tags;
    }

    private List<Despesa> getListaDespesas() {
        if (listaDespesas == null) {
            listaDespesas = new DespesaDao().busca();
        }
        return listaDespesas;
    }

    private SortedSet<String> getListaTags() {
        if (listaTags == null) {
            listaTags = new TagsDao().busca();
        }
        return listaTags;
    }
}
