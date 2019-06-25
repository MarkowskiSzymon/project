package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class CardModelTest {

    public static List<CardModelTest> listOfCards = new ArrayList<>();
    private String nr;
    private String pic;

    public CardModelTest() {

    }

    public CardModelTest(String nr, String pic) {
        this.nr = nr;
        this.pic = pic;
    }

    public void addToCardList(String nr, String pic){
        this.nr = nr;
        this.pic = pic;
        listOfCards.add(new CardModelTest(nr, pic));
    }


    public static List<CardModelTest> getListOfCards() {
        return listOfCards;
    }

    public static void setListOfCards(List<CardModelTest> listOfCards) {
        CardModelTest.listOfCards = listOfCards;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
