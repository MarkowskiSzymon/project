package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionsModel {


    public static List<TransactionsModel> listOfTransactions = new ArrayList<>();
    private String data;
    private String typ_transakcji_id;
    private String koszt_punktow;
    private String kwota_zakupow;
    private String nr_karty;
    private String pic;
    private String nazwa;

    public TransactionsModel(){
    }

    public TransactionsModel(String data, String typ_transakcji_id, String koszt_punktow, String kwota_zakupow, String nr_karty, String pic, String nazwa) {
        this.data = data;
        this.typ_transakcji_id = typ_transakcji_id;
        this.koszt_punktow = koszt_punktow;
        this.kwota_zakupow = kwota_zakupow;
        this.nr_karty = nr_karty;
        this.pic = pic;
        this.nazwa = nazwa;

    }

    public void addToListOfTransactions(String data, String typ_transakcji_id, String koszt_punktow, String kwota_zakupow, String nr_karty, String pic, String nazwa){
        this.data = data;
        this.typ_transakcji_id = typ_transakcji_id;
        this.koszt_punktow = koszt_punktow;
        this.kwota_zakupow = kwota_zakupow;
        this.nr_karty = nr_karty;
        this.pic = pic;
        this.nazwa = nazwa;
        listOfTransactions.add(new TransactionsModel(data, typ_transakcji_id, koszt_punktow, kwota_zakupow, nr_karty, pic, nazwa));
    }


    public static List<TransactionsModel> getListOfTransactions() {
        return listOfTransactions;
    }

    public static void setListOfTransactions(List<TransactionsModel> listOfTransactions) {
        TransactionsModel.listOfTransactions = listOfTransactions;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTyp_transakcji_id() {
        return typ_transakcji_id;
    }

    public void setTyp_transakcji_id(String typ_transakcji_id) {
        this.typ_transakcji_id = typ_transakcji_id;
    }

    public String getKoszt_punktow() {
        return koszt_punktow;
    }

    public void setKoszt_punktow(String koszt_punktow) {
        this.koszt_punktow = koszt_punktow;
    }

    public String getKwota_zakupow() {
        return kwota_zakupow;
    }

    public void setKwota_zakupow(String kwota_zakupow) {
        this.kwota_zakupow = kwota_zakupow;
    }

    public String getNr_karty() {
        return nr_karty;
    }

    public void setNr_karty(String nr_karty) {
        this.nr_karty = nr_karty;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }




}
