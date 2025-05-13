package com.example.project_1.dataModels.UI;

public class NavItem {
    private String name;
    private String url;
    private String icon;

    public NavItem(String name, String url) {
        this.name = name;
        this.url = url;
        this.icon = ""; // Default empty icon
    }

    public NavItem(String name, String url, String icon) {
        this.name = name;
        this.url = url;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}