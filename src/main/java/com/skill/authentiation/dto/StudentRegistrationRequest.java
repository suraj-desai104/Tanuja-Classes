package com.skill.authentiation.dto;

public class StudentRegistrationRequest {

    private String fullName;
    private String email;
    private String phone;
    private String standard; // e.g., 8th, 9th, 10th
    private String course;   // e.g., Spoken English, Grammar

    // Constructors
    public StudentRegistrationRequest() {}

    public StudentRegistrationRequest(String fullName, String email, String phone, String standard, String course) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.standard = standard;
        this.course = course;
    }

    // Getters & Setters
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStandard() {
        return standard;
    }
    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
}
