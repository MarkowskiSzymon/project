package com.example.project.model;

import java.util.ArrayList;

public class PartnersModel {
    String id = null;
    String wid = null;
    String nazwa = null;
    String alt = null;
    String lat = null;
    String opis = null;
    String pic = null;
    String miasto = null;
    String przelicznik = null;
    String ilosc_pkt = null;
    public static ArrayList<String> mPartners_Id = new ArrayList<>();
    public static ArrayList<String> mPartners_Wid = new ArrayList<>();
    public static ArrayList<String> mPartners_Name = new ArrayList<>();
    public static ArrayList<String> mPartners_Longitude = new ArrayList<>();
    public static ArrayList<String> mPartners_Latitude = new ArrayList<>();
    public static ArrayList<String> mPartners_Desc = new ArrayList<>();
    public static ArrayList<String> mPartners_Picture = new ArrayList<>();
    public static ArrayList<String> mPartners_City = new ArrayList<>();
    public static ArrayList<String> mPartners_Multiplier = new ArrayList<>();
    public static ArrayList<String> mPartners_OwnedPoints = new ArrayList<>();


    public PartnersModel(){
    }

    public void clearAllList(){
        this.mPartners_Id.clear();
        this.mPartners_Wid.clear();
        this.mPartners_Name.clear();
        this.mPartners_Longitude.clear();
        this.mPartners_Latitude.clear();
        this.mPartners_Desc.clear();
        this.mPartners_Picture.clear();
        this.mPartners_City.clear();
        this.mPartners_Multiplier.clear();
        this.mPartners_OwnedPoints.clear();

    }

    public void addToListPartnersId(String string){
        this.mPartners_Id.add(string);
    }
    public void addToListPartnersWId(String string){
        this.mPartners_Wid.add(string);
    }
    public void addToListPartnersName(String string){
        this.mPartners_Name.add(string);
    }
    public void addToListPartnersLongitude(String string){
        this.mPartners_Longitude.add(string);
    }
    public void addToListPartnersLatitude(String string){
        this.mPartners_Latitude.add(string);
    }
    public void addToListPartnersDescription(String string){
        this.mPartners_Desc.add(string);
    }
    public void addToListPartnersPicture(String string){
        this.mPartners_Picture.add(string);
    }
    public void addToListPartnersCity(String string){
        this.mPartners_City.add(string);
    }
    public void addToListPartnersMultiplier(String string){
        this.mPartners_Multiplier.add(string);
    }
    public void addToListPartnersOwnedPoints(String string){
        this.mPartners_OwnedPoints.add(string);
    }


    public static ArrayList<String> getmPartners_Id() {
        return mPartners_Id;
    }


    public static ArrayList<String> getmPartners_Wid() {
        return mPartners_Wid;
    }


    public static ArrayList<String> getmPartners_Name() {
        return mPartners_Name;
    }


    public static ArrayList<String> getmPartners_Longitude() {
        return mPartners_Longitude;
    }


    public static ArrayList<String> getmPartners_Latitude() {
        return mPartners_Latitude;
    }


    public static ArrayList<String> getmPartners_Picture() {
        return mPartners_Picture;
    }


    public static ArrayList<String> getmPartners_Desc() {
        return mPartners_Desc;
    }


    public static ArrayList<String> getmPartners_City() {
        return mPartners_City;
    }


    public static ArrayList<String> getmPartners_Multiplier() {
        return mPartners_Multiplier;
    }


    public static ArrayList<String> getmPartners_OwnedPoints() {
        return mPartners_OwnedPoints;
    }

}
