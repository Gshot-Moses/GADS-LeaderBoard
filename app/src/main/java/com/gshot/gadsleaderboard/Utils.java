package com.gshot.gadsleaderboard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.gshot.gadsleaderboard.models.HoursLeader;
import com.gshot.gadsleaderboard.models.SkillIQLeader;

import java.util.Collections;
import java.util.List;

public class Utils {

    public static List<HoursLeader> sortHoursList(List<HoursLeader> hoursLeaders) {
        Collections.sort(hoursLeaders, (o1, o2) -> {
            if (Integer.valueOf(o1.getHours()) < Integer.valueOf(o2.getHours()))
                return 1;
            else if (Integer.valueOf(o1.getHours()) > Integer.valueOf(o2.getHours()))
                return -1;
            else
                return 0;
        });
        return hoursLeaders;
    }

    public static List<SkillIQLeader> sortScoresList(List<SkillIQLeader> skillIQLeaders) {
        Collections.sort(skillIQLeaders, (o1, o2) -> {
            if (Integer.valueOf(o1.getScore()) < Integer.valueOf(o2.getScore()))
                return 1;
            else if (Integer.valueOf(o1.getScore()) > Integer.valueOf(o2.getScore()))
                return -1;
            else
                return 0;
        });
        return skillIQLeaders;
    }

    public static boolean checkConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mobileInfo.isConnected() || wifiInfo.isConnected();
    }
}
