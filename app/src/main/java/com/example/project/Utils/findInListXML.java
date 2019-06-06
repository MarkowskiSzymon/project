package com.example.project.Utils;

import com.example.project.model.OneNode;

import java.util.List;

public class findInListXML {

    public String findInList(String name, List<OneNode> node) {
        for (int i = 0; i < node.size(); i++) {
            if (node.get(i).getTag().equals(name)) {
                return node.get(i).getValue();
            }
        }
        return null;
    }
}
