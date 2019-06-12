package com.example.project.model;

import java.util.ArrayList;

public class HistoryModel {
    public static ArrayList<String> mTransactionData = new ArrayList<>();

    public static ArrayList<String> mTransactionType = new ArrayList<>();
    public static ArrayList<String> mTransactionExpense = new ArrayList<>();
    public static ArrayList<String> mTransactionAmount = new ArrayList<>();
    public static ArrayList<String> mTransactionCardNumber = new ArrayList<>();
    public static ArrayList<String> mTransactionPicture = new ArrayList<>();
    public static ArrayList<String> mTransactionName = new ArrayList<>();

    public HistoryModel(){
    }

    public void clearAllList(){
        this.mTransactionData.clear();
        this.mTransactionType.clear();
        this.mTransactionExpense.clear();
        this.mTransactionAmount.clear();
        this.mTransactionCardNumber.clear();
        this.mTransactionPicture.clear();
        this.mTransactionName.clear();
    }

    public void addToListTransactionData(String string){
        this.mTransactionData.add(string);
    }

    public void addToListTransactionType(String string){
        this.mTransactionType.add(string);
    }

    public void addToListTransactionExpense(String string){
        this.mTransactionExpense.add(string);
    }

    public void addToListTransactionAmount(String string){
        this.mTransactionAmount.add(string);
    }

    public void addToListTransactionPicture(String string){
        this.mTransactionPicture.add(string);
    }

    public void addToListTransactionName(String string){
        this.mTransactionName.add(string);
    }

    public void addToListTransactionCardNumber(String string){
        this.mTransactionCardNumber.add(string);
    }


    public static ArrayList<String> getmTransactionData() {
        return mTransactionData;
    }

    public static ArrayList<String> getmTransactionType() {
        return mTransactionType;
    }

    public static ArrayList<String> getmTransactionExpense() {
        return mTransactionExpense;
    }

    public static ArrayList<String> getmTransactionAmount() {
        return mTransactionAmount;
    }

    public static ArrayList<String> getmTransactionCardNumber() {
        return mTransactionCardNumber;
    }

    public static ArrayList<String> getmTransactionPicture() {
        return mTransactionPicture;
    }

    public static ArrayList<String> getmTransactionName() {
        return mTransactionName;
    }

}
