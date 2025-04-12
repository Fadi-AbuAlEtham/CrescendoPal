package com.example.crescendopal.data;

import androidx.annotation.NonNull;

import java.util.List;

public class Teacher {
    private String id;
    private String name;
    private String instrument;
    private String location;
    private boolean isOnlineAvailable;
    private boolean offersTrial;
    private double hourlyRate;
    private List<String> availableDays; // e.g., ["Monday", "Wednesday"]
    private List<String> availableTimes; // e.g., ["4PM", "6PM"]
    private int profileImageResId;

    public Teacher() {
    }

    public Teacher(String id, String name, String instrument, String location, boolean isOnlineAvailable, boolean offersTrial, double hourlyRate, List<String> availableDays, List<String> availableTimes, int profileImageResId) {
        this.id = id;
        this.name = name;
        this.instrument = instrument;
        this.location = location;
        this.isOnlineAvailable = isOnlineAvailable;
        this.offersTrial = offersTrial;
        this.hourlyRate = hourlyRate;
        this.availableDays = availableDays;
        this.availableTimes = availableTimes;
        this.profileImageResId = profileImageResId;
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

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isOnlineAvailable() {
        return isOnlineAvailable;
    }

    public void setOnlineAvailable(boolean onlineAvailable) {
        isOnlineAvailable = onlineAvailable;
    }

    public boolean isOffersTrial() {
        return offersTrial;
    }

    public void setOffersTrial(boolean offersTrial) {
        this.offersTrial = offersTrial;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<String> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }

    public void setProfileImageResId(int profileImageResId) {
        this.profileImageResId = profileImageResId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", instrument='" + instrument + '\'' +
                ", location='" + location + '\'' +
                ", isOnlineAvailable=" + isOnlineAvailable +
                ", offersTrial=" + offersTrial +
                ", hourlyRate=" + hourlyRate +
                ", availableDays=" + availableDays +
                ", availableTimes=" + availableTimes +
                ", profileImageResId=" + profileImageResId +
                '}';
    }
}
