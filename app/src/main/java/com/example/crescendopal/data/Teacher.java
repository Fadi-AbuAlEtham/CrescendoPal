package com.example.crescendopal.data;

import androidx.annotation.NonNull;

import java.util.List;

public class Teacher extends User{
    private List<String> instrument;
    private boolean offersTrial;
    private double hourlyRate;
    private boolean isOnlineAvailable;
    private List<String> availableDays;
    private List<String> availableTimes;

    public Teacher() {
    }

    public Teacher(String id, String name, List<String> instrument, String location, boolean isOnlineAvailable, boolean offersTrial, double hourlyRate, List<String> availableDays, List<String> availableTimes, int profileImageResId) {
        this.instrument = instrument;
        this.isOnlineAvailable = isOnlineAvailable;
        this.offersTrial = offersTrial;
        this.hourlyRate = hourlyRate;
        this.availableDays = availableDays;
        this.availableTimes = availableTimes;
    }


    public List<String> getInstrument() {
        return instrument;
    }

    public void setInstrument(List<String> instrument) {
        this.instrument = instrument;
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

    @NonNull
    @Override
    public String toString() {
        return "Teacher{" +
                ", instrument='" + instrument + '\'' +
                ", isOnlineAvailable=" + isOnlineAvailable +
                ", offersTrial=" + offersTrial +
                ", hourlyRate=" + hourlyRate +
                ", availableDays=" + availableDays +
                ", availableTimes=" + availableTimes +
                '}';
    }
}
