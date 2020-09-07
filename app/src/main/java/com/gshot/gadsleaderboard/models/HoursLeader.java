package com.gshot.gadsleaderboard.models;

import com.google.gson.annotations.Expose;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HoursLeader {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @Expose
    private String name;

    @Expose
    private String hours;

    @Expose
    private String country;

    @Expose
    private String badgeUrl;

    public HoursLeader() {
    }

    public HoursLeader(String name, String hours, String country, String badgeUrl) {
        this.name = name;
        this.hours = hours;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

    public String hoursCountry() {
        return getHours() + " learning hours, " + getCountry();
    }
}
