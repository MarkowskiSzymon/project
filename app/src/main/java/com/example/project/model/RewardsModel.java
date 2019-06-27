package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class RewardsModel {

    public List<RewardsModel> listOfPromo = new ArrayList<>();
    public String name;
    public String points;
    public int length;

    public RewardsModel() {

    }

    public RewardsModel(String name, String points, int length) {
        this.name = name;
        this.points = points;
        this.length = length;
    }

    public void addToPromoList(String name, String points, int length){
        this.name = name;
        this.points = points;
        listOfPromo.add(new RewardsModel(name, points, length));
    }

    public List<RewardsModel> getListOfPromo() {
        return listOfPromo;
    }

    public void setListOfPromo(List<RewardsModel> listOfPromo) {
        this.listOfPromo = listOfPromo;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}