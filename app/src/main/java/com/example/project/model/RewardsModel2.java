package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class RewardsModel2 {

    public List<RewardsModel2> listOfRewards2 = new ArrayList<>();
    public String name;
    public String points;

    public RewardsModel2() {

    }

    public RewardsModel2(String name, String points) {
        this.name = name;
        this.points = points;
    }

    public void addToRewardsList(String name, String points){
        this.name = name;
        this.points = points;
        listOfRewards2.add(new RewardsModel2(name, points));
    }

    public List<RewardsModel2> getListOfRewards2() {
        return listOfRewards2;
    }

    public void setListOfRewards2(List<RewardsModel2> listOfRewards2) {
        this.listOfRewards2 = listOfRewards2;
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