package com.example.project.model;

import java.util.ArrayList;
import java.util.List;

public class AllNodes {



    String tag;
    public List<OneNode> nodeList;
    public List<AllNodes> extendedNodeList;
    public AllNodes extendedNode;

    public AllNodes(){
        this.tag = "";
        this.nodeList = new ArrayList<>();
        this.extendedNode = null;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void addToList(OneNode node){
        this.nodeList.add(node);
    }

    public void getSize(OneNode node){
        this.nodeList.add(node);
    }

    public void addToExtendedList(AllNodes extendedNodes){
        this.extendedNodeList.add(extendedNodes);
    }


    public void addExtendedNode(AllNodes extendedNode){
        this.extendedNode = extendedNode;
    }

    @Override
    public String toString() {
        return "AllNodes{" +
                "tag='" + tag + '\'' +
                ", nodeList=" + nodeList +
                ", extendedNode=" + extendedNode +
                ", extebdedNodeList=" + extendedNodeList +
                '}';
    }
}
