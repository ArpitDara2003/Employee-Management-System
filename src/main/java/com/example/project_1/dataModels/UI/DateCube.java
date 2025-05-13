package com.example.project_1.dataModels.UI;
import java.util.List;

public class DateCube {
    private int day;
    private int month;
    private int year;
    private List<String> events;
    private String weekDay;

    
    public DateCube(int day, int month, int year, List<String> events, String weekDay) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.events = events;
        this.weekDay = weekDay;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public List<String> getEvents() {
        return events;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

}
