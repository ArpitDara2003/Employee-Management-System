package com.example.project_1.dataModels.UI;

public class EmployeeView {
    private int id;
    private String name;
    private String email;
    private String position;
    private String department;
    private String joinDate;
    private String profileImage;
    
    public EmployeeView(int id, String name, String email, String position, String department, String joinDate, String profileImage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.department = department;
        this.joinDate = joinDate;
        this.profileImage = profileImage;
    }
    
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPosition() { return position; }
    public String getDepartment() { return department; }
    public String getJoinDate() { return joinDate; }
    public String getProfileImage() { return profileImage; }
}