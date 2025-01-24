package com.example.project_1.dataModels.UI;

public class NavItem {

    private String name;
    private String page;

    // Constructor
    public NavItem(String name, String page) {
        this.name = name;
        this.page = page;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}