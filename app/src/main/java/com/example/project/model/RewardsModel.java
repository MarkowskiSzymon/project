package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class RewardsModel {

    RewardsModel2 rewardsModel2 = new RewardsModel2();
    public static List<RewardsModel> listOfRewards = new ArrayList<>();
    public List<RewardsModel2> listOfReward = rewardsModel2.listOfRewards2;

    public RewardsModel() {

    }

    public RewardsModel(List<RewardsModel2> listOfReward){
        this.listOfReward = listOfReward;
    }


    public void addToInformationList(List<RewardsModel2> listOfCards){

        this.listOfReward = listOfCards;
        listOfRewards.add(new RewardsModel(listOfCards));
    }


    public List<RewardsModel2> getListOfReward() {
        return listOfReward;
    }

    public RewardsModel2 getRewardsModel2() {
        return rewardsModel2;
    }

    public void setRewardsModel2(RewardsModel2 rewardsModel2) {
        this.rewardsModel2 = rewardsModel2;
    }

    public void setListOfReward(List<RewardsModel2> listOfReward) {
        this.listOfReward = listOfReward;
    }

    public List<RewardsModel> getListOfRewards() {
        return listOfRewards;
    }

    public void setListOfRewards(List<RewardsModel> listOfRewards) {
        this.listOfRewards = listOfRewards;
    }
}