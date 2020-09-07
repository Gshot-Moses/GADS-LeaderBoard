package com.gshot.gadsleaderboard.persistence;

import com.gshot.gadsleaderboard.models.HoursLeader;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface HoursLeadersDAO {

    @Insert
    void insertHoursLeaders(List<HoursLeader> leaders);

    @Query("SELECT * FROM HoursLeader")
    List<HoursLeader> getHoursLeaders();

    @Delete
    void deleteRecords(List<HoursLeader> leaders);
}
