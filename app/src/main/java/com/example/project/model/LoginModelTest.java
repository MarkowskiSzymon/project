package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class LoginModelTest {
    public static List<LoginModelTest> listOfInformation = new ArrayList<>();
    private List<CardModelTest> listOfCard = CardModelTest.listOfCards;
    private String id;
    private String imie;
    private String telefon;
    private String email;
    private String kod_pocztowy;
    private String data_urodzenia;
    private String plec;
    private String data_rejestracji;
    private String czy_zarejestrowany;
    private String czy_aktywny;

    public LoginModelTest() {

    }

    public LoginModelTest(String id, String imie, String telefon, String email, String kod_pocztowy, String data_urodzenia, String plec, String data_rejestracji, String czy_zarejestrowany, String czy_aktywny, List<CardModelTest> listOfCard) {
        this.id = id;
        this.imie = imie;
        this.telefon = telefon;
        this.email = email;
        this.kod_pocztowy = kod_pocztowy;
        this.data_urodzenia = data_urodzenia;
        this.plec = plec;
        this.data_rejestracji = data_rejestracji;
        this.czy_zarejestrowany = czy_zarejestrowany;
        this.czy_aktywny = czy_aktywny;
        this.listOfCard = listOfCard;
    }

    public void addToInformationList(String id, String imie, String telefon, String email, String kod_pocztowy, String data_urodzenia, String plec, String data_rejestracji, String czy_zarejestrowany, String czy_aktywny, List<CardModelTest> listOfCards){
        this.id = id;
        this.imie = imie;
        this.telefon = telefon;
        this.email = email;
        this.kod_pocztowy = kod_pocztowy;
        this.data_urodzenia = data_urodzenia;
        this.plec = plec;
        this.data_rejestracji = data_rejestracji;
        this.czy_zarejestrowany = czy_zarejestrowany;
        this.czy_aktywny = czy_aktywny;
        this.listOfCard = listOfCards;

        listOfInformation.add(new LoginModelTest(id, imie, telefon, email, kod_pocztowy, data_urodzenia, plec, data_rejestracji, czy_zarejestrowany, czy_aktywny, listOfCards));
    }

    public static List<LoginModelTest> getListOfInformation() {
        return listOfInformation;
    }

    public static void setListOfInformation(List<LoginModelTest> listOfInformation) {
        LoginModelTest.listOfInformation = listOfInformation;
    }

    public List<CardModelTest> getListOfCard() {
        return listOfCard;
    }

    public void setListOfCard(List<CardModelTest> listOfCard) {
        this.listOfCard = listOfCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKod_pocztowy() {
        return kod_pocztowy;
    }

    public void setKod_pocztowy(String kod_pocztowy) {
        this.kod_pocztowy = kod_pocztowy;
    }

    public String getData_urodzenia() {
        return data_urodzenia;
    }

    public void setData_urodzenia(String data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getData_rejestracji() {
        return data_rejestracji;
    }

    public void setData_rejestracji(String data_rejestracji) {
        this.data_rejestracji = data_rejestracji;
    }

    public String getCzy_zarejestrowany() {
        return czy_zarejestrowany;
    }

    public void setCzy_zarejestrowany(String czy_zarejestrowany) {
        this.czy_zarejestrowany = czy_zarejestrowany;
    }

    public String getCzy_aktywny() {
        return czy_aktywny;
    }

    public void setCzy_aktywny(String czy_aktywny) {
        this.czy_aktywny = czy_aktywny;
    }

}
