package ru.geographer29.pppdownloader.entities;

import java.util.List;

public class LinksTable {

    private List<String> links;
    private String name;

    public LinksTable(List<String> links, String name) {
        this.links = links;
        this.name = name;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
