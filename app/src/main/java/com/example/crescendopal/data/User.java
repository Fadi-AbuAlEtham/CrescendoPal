package com.example.crescendopal.data;

import androidx.annotation.NonNull;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String role; // "student", "teacher", "guest", "musician", etc.
    private String profilePictureUrl;
    private boolean isVerified;
    private String username;
    private String password;

    public User() {
    }

    public User(String id, String name, String email, String phone, String role, String profilePictureUrl, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.profilePictureUrl = profilePictureUrl;
        this.isVerified = isVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", isVerified=" + isVerified +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

