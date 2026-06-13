package com.nhs.automation.framework.models;

import java.time.LocalDate;

public class Job {
    private String title;
    private String location;
    private double distance;
    private LocalDate postedDate;
    public Job(String title, String location, double distance, LocalDate postedDate) {
        this.title = title;
        this.location = location;
        this.distance = distance;
        this.postedDate = postedDate;
    }
    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }
}