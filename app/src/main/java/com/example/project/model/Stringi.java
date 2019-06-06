package com.example.project.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Stringi {

    String values;
    String tags;
    public List<String> lista = new ArrayList<>();

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public void addToList(String values){
        this.lista.add(values);
    }

}
