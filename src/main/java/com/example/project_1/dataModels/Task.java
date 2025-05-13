package com.example.project_1.dataModels;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "complexity", nullable = false)
    private int complexity;

    @Column(name = "time_spent", nullable = false)
    private int timeSpent;

    @Column(name = "estimated_time_required", nullable = false)
    private int estimatedTimeRequired;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "percentage_completed", nullable = false)
    private int percentageCompleted;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "taskId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> subTasks;

    public Task() {
        // Default constructor for JPA
    }

    public Task(String title, String description, int complexity, int estimatedTimeRequired, String status, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.complexity = complexity;
        this.estimatedTimeRequired = estimatedTimeRequired;
        this.status = status;
        this.dueDate = dueDate;
    }

    public Task(String title, String description, int complexity, int timeSpent, int estimatedTimeRequired, 
            String status, LocalDate dueDate, Employee employee, Company company) {
        this.title = title;
        this.description = description;
        this.complexity = complexity;
        this.timeSpent = timeSpent;
        this.estimatedTimeRequired = estimatedTimeRequired;
        this.status = status;
        this.dueDate = dueDate;
        this.employee = employee;
        this.company = company;
    }


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
        updatePercentageCompleted();
    }

    public int getEstimatedTimeRequired() {
        return estimatedTimeRequired;
    }

    public void setEstimatedTimeRequired(int estimatedTimeRequired) {
        this.estimatedTimeRequired = estimatedTimeRequired;
        updatePercentageCompleted();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }

    public int getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(int percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    private void updatePercentageCompleted() {
        this.percentageCompleted = (int) ((double) timeSpent / estimatedTimeRequired * 100);
    }
}