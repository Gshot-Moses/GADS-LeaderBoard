package com.gshot.gadsleaderboard.remote;

import com.gshot.gadsleaderboard.models.HoursLeader;
import com.gshot.gadsleaderboard.models.SkillIQLeader;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GadsAPI {

    @GET("api/hours")
    Call<List<HoursLeader>> getHoursLeaders();

    @GET("api/skilliq")
    Call<List<SkillIQLeader>> getSkillIQLeaders();
}
