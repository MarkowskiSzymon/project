package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class RewardsModel {

    public List<RewardsModel> listOfRewards = new ArrayList<>();
    public List<String> listaNazw = new ArrayList<>();
    public List<String> listaPunktow = new ArrayList<>();
    public String name;
    public String points;

    public RewardsModel() {

    }

    public RewardsModel(String name, String points) {
        this.name = name;
        this.points = points;
    }

    public void addToRewardsList(String name, String points){
        this.name = name;
        this.points = points;
        listOfRewards.add(new RewardsModel(name, points));
    }

    public List<RewardsModel> getListOfRewards() {
        return listOfRewards;
    }

    public void setListOfRewards(List<RewardsModel> listOfRewards) {
        this.listOfRewards = listOfRewards;
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