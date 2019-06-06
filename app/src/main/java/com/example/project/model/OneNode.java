package com.example.project.model;

public class OneNode {
    String tag;
    String value;
    Boolean hasChildrens;

    public OneNode(){
        this.tag = "";
        this.value = "";
        this.hasChildrens = true;
    }

    public Boolean getHasChildrens() {
        return hasChildrens;
    }

    public void setHasChildrens(Boolean hasChildrens) {
        this.hasChildrens = hasChildrens;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



}
