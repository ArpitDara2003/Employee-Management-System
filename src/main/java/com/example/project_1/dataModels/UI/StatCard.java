package com.example.project_1.dataModels.UI;

import java.util.ArrayList;
import java.util.List;

public class StatCard {
    private String iconClass;
    private String color;
    private String title;
    private String value;

    public StatCard(String iconClass, String color, String title, String value) {
        this.iconClass = iconClass;
        this.color = color;
        this.title = title;
        this.value = value;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
