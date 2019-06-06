package com.example.project.model;

import java.util.ArrayList;

public class CardsModel {

    public static ArrayList<String> mCardsNumber = new ArrayList<>();
    public static ArrayList<String> mCardsIcon = new ArrayList<>();

    public CardsModel(){
    }

    public void clearAllList(){
        this.mCardsNumber.clear();
        this.mCardsIcon.clear();
    }

    public void addToListCardsNumber(String string){
        this.mCardsNumber.add(string);
    }

    public void addToListCardsIcon(String string){
        this.mCardsIcon.add(string);
    }



    public static ArrayList<String> getmCardsNumber() {
        return mCardsNumber;
    }

    public static ArrayList<String> getmCardsIcon() {
        return mCardsIcon;
    }



}
