package com.example.project_1.dataModels.UI;

import java.util.Map;

public class WorkLog {
    private String label;   // The question text
    private String type;    // Type of input (text, int, date, range, etc.)
    private String name;    // Unique identifier for the input field
    private String value;   // Default value for range input (percentage)
    private Map<String, String> projectDueDates; // Map for dropdown options with due dates

    // Constructor for normal input types
    public WorkLog(String label, String type, String name) {
        this.label = label;
        this.type = type;
        this.name = name;
    }

    // Constructor for dropdown input type with due dates
    public WorkLog(String label, String type, String name, Map<String, String> projectDueDates) {
        this.label = label;
        this.type = type;
        this.name = name;
        this.projectDueDates = projectDueDates;
    }

    // Constructor for range input type
    public WorkLog(String label, String type, String name, String value) {
        this.label = label;
        this.type = type;
        this.name = name;
        this.value = value;
    }

    // Getters and setters
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Map<String, String> getProjectDueDates() {
        return projectDueDates;
    }

    public void setProjectDueDates(Map<String, String> projectDueDates) {
        this.projectDueDates = projectDueDates;
    }
}
