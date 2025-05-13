package com.example.project_1.dataModels.UI;

public class Achievement {
    private String title;
    private String date;
    private String location;
    private String type; // bronze, silver, gold, diamond

    public Achievement(String title, String date, String location, String type) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.type = type;
    }

    // getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
