package com.example.project_1.dataModels.UI;

public class TeamMember {
    private String employeeId; // Unique identifier for the employee
    private String name;
    private String position;
    private String email;
    private String phoneNumber;
    private String profileImage;
    private String status; // example: "active", "inactive", "on-leave", etc.

    // Constructors
    public TeamMember() {}

    public TeamMember(String employeeId, String name, String position, String email, String phoneNumber, String profileImage, String status) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.status = status;
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
