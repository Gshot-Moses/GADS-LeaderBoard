package com.gshot.gadsleaderboard.adapters;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gshot.gadsleaderboard.models.HoursLeader;
import com.gshot.gadsleaderboard.models.SkillIQLeader;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BindingAdapters {

    @BindingAdapter("hourLeadersList")
    public static void hoursLeadersList(RecyclerView recyclerView, List<HoursLeader> leaders) {
        if (leaders == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(layoutManager);
        }
        HourLeadersRecyclerAdapter adapter = (HourLeadersRecyclerAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new HourLeadersRecyclerAdapter(recyclerView.getContext(), leaders);
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("skillLeadersList")
    public static void skillLeadersList(RecyclerView recyclerView, List<SkillIQLeader> leaders) {
        if (leaders == null) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.setLayoutManager(layoutManager);
        }
        SkillLeaderRecyclerAdapter adapter = (SkillLeaderRecyclerAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            adapter = new SkillLeaderRecyclerAdapter(recyclerView.getContext(), leaders);
            recyclerView.setAdapter(adapter);
        }
    }

    @BindingAdapter("setImageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .into(imageView);
    }
}
