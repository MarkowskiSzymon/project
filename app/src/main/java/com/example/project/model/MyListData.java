package com.example.project.model;


import java.util.ArrayList;
import java.util.List;

public class MyListData {

    public static List<MyListData> listOfPartners = new ArrayList<>();
    private String id;
    private String wid;
    private String name;
    private String alt;
    private String lat;
    private String desc;
    private String pic;
    private String miasto;
    private String przelicznik;
    private String ilosc_pkt;
    private String distanceToPartner;

    public MyListData() {

    }

    public MyListData(String id, String wid, String name, String alt, String lat, String desc, String pic, String miasto, String przelicznik, String ilosc_pkt, String distanceToPartner) {
        this.id = id;
        this.wid = wid;
        this.name = name;
        this.alt = alt;
        this.lat = lat;
        this.desc = desc;
        this.pic = pic;
        this.miasto = miasto;
        this.przelicznik = przelicznik;
        this.ilosc_pkt = ilosc_pkt;
        this.distanceToPartner = distanceToPartner;
    }

    public void addToExampleList(String id, String wid, String nazwa, String alt, String lat, String desc, String pic, String miasto, String przelicznik, String ilosc_pkt, String distanceToPartner){
        this.id = id;
        this.wid = wid;
        this.name = nazwa;
        this.alt = alt;
        this.lat = lat;
        this.desc = desc;
        this.pic = pic;
        this.miasto = miasto;
        this.przelicznik = przelicznik;
        this.ilosc_pkt = ilosc_pkt;
        this.distanceToPartner = distanceToPartner;
        listOfPartners.add(new MyListData(id, wid, nazwa, alt, lat, desc, pic, miasto, przelicznik, ilosc_pkt, distanceToPartner));
    }

    public int exampleListSize(){
        return listOfPartners.size();
    }

    public String getDistanceToPartner() {
        return distanceToPartner;
    }

    public void setDistanceToPartner(String distanceToPartner) {
        this.distanceToPartner = distanceToPartner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getOpis() {
        return desc;
    }

    public void setOpis(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getPrzelicznik() {
        return przelicznik;
    }

    public void setPrzelicznik(String przelicznik) {
        this.przelicznik = przelicznik;
    }

    public String getIlosc_pkt() {
        return ilosc_pkt;
    }

    public void setIlosc_pkt(String ilosc_pkt) {
        this.ilosc_pkt = ilosc_pkt;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
