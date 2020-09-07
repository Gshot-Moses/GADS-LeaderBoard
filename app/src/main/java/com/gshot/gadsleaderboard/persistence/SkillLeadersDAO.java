package com.gshot.gadsleaderboard.persistence;


import com.gshot.gadsleaderboard.models.SkillIQLeader;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SkillLeadersDAO {

    @Insert
    void insertSkillLeaders(List<SkillIQLeader> leaders);

    @Query("SELECT * FROM SkillIQLeader")
    List<SkillIQLeader> getSkillLeaders();

    @Delete
    void deleteRecords(List<SkillIQLeader> leaders);
}
