package fr.aylan.dailycollect.driver.model;

import java.util.Date;

public class Tour {

    private int id;
    private String collectPointName;
    private String date;
    private String minTime;
    private String maxTime;
    private String city;

    public Tour(int id, String collectPointName, String date, String minTime, String maxTime, String city) {
        this.id = id;
        this.collectPointName = collectPointName;
        this.date = date;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectPointName() {
        return collectPointName;
    }

    public void setCollectPointName(String collectPointName) {
        this.collectPointName = collectPointName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMinTime() {
        return minTime;
    }

    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
