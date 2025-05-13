package com.example.project_1.dataModels;

import jakarta.persistence.*;

@Entity
@Table(name = "performance_metrics")
public class PerformanceMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "value", nullable = false)
    private int value;

    @Column(name = "max_value", nullable = false)
    private int maxValue;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public PerformanceMetrics(String title, int value, int maxValue, Employee employee, Company company) {
        this.title = title;
        this.value = value;
        this.maxValue = maxValue;
        this.employee = employee;
        this.company = company;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // Method to calculate percentage
    public double getPercentage() {
        if (maxValue == 0) {
            return 0; // Avoid division by zero
        }
        return (value / (double) maxValue) * 100;
    }
}