package com.gshot.gadsleaderboard.models;

import com.google.gson.annotations.Expose;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SkillIQLeader {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @Expose
    private String name;

    @Expose
    private String score;

    @Expose
    private String country;

    @ColumnInfo(name = "badge_url")
    @Expose
    private String badgeUrl;

    public SkillIQLeader() {
    }

    public SkillIQLeader(String name, String score, String country, String badgeUrl) {
        this.name = name;
        this.score = score;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String scoreCountry() {
        return getScore() + " skill IQ Score, " + getCountry();
    }
}
