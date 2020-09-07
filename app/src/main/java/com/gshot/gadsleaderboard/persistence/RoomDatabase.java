package com.gshot.gadsleaderboard.persistence;

import android.content.Context;

import com.gshot.gadsleaderboard.models.HoursLeader;
import com.gshot.gadsleaderboard.models.SkillIQLeader;

import androidx.room.Database;
import androidx.room.Room;

@Database(entities = {HoursLeader.class, SkillIQLeader.class}, version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    private static RoomDatabase database;

    private static final String DATABASE_NAME = "leaderBoard";

    public static RoomDatabase getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, DATABASE_NAME).build();
        }
        return database;
    }

    public abstract SkillLeadersDAO getSkillDAO();

    public abstract HoursLeadersDAO getHoursDAO();

}
