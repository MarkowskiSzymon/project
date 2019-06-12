package com.example.project.model;

import java.util.ArrayList;

public class ElemModel {

    public static ArrayList<String> listaStringow = new ArrayList<>();
    public static ArrayList<String> mCardNumberList = new ArrayList<>();
    public static ArrayList<String> mCardIconList = new ArrayList<>();

    public ElemModel(){
    }

    public void clearAllList(){
        mCardNumberList.clear();
        mCardIconList.clear();
    }


    public ArrayList<String> getmCardNumberList() {
        return mCardNumberList;
    }
    public ArrayList<String> getmCardIconList() {
        return mCardIconList;
    }

    public static ArrayList<String> getListaStringow() {
        return listaStringow;
    }

    public void addToListValues(String string){
        this.listaStringow.add(string);
    }

    public void addToListCardNumbers(String string){
        this.mCardNumberList.add(string);
    }

    public void addToListCardIcons(String string){
        this.mCardIconList.add(string);
    }




    @Override
    public String toString() {
        return super.toString();
    }

}
