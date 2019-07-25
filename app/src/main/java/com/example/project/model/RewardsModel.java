package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class RewardsModel {

    public static List<RewardsModel> listOfPromo = new ArrayList<>();
    public String name;
    public String points;
    public String nid;
    public String bid;

    public RewardsModel() {

    }

    public RewardsModel(String name, String points, String nid, String bid) {
        this.name = name;
        this.points = points;
        this.nid = nid;
        this.bid = bid;
    }

    public void addToPromoList(String name, String points, String nid, String bid){
        this.name = name;
        this.points = points;
        this.nid = nid;
        this.bid = bid;
        listOfPromo.add(new RewardsModel(name, points, nid, bid));
    }

    public List<RewardsModel> getListOfPromo() {
        return listOfPromo;
    }

    public void setListOfPromo(List<RewardsModel> listOfPromo) {
        this.listOfPromo = listOfPromo;
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

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}