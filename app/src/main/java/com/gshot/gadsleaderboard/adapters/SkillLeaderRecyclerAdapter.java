package com.gshot.gadsleaderboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gshot.gadsleaderboard.R;
import com.gshot.gadsleaderboard.models.SkillIQLeader;
import com.gshot.gadsleaderboard.databinding.SkillLeaderItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class SkillLeaderRecyclerAdapter extends RecyclerView.Adapter<SkillLeaderRecyclerAdapter.ViewHolder> {

    Context context;
    List<SkillIQLeader> leaderList;

    public SkillLeaderRecyclerAdapter(Context context, List<SkillIQLeader> leaderList) {
        this.context = context;
        this.leaderList = leaderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SkillLeaderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.skill_leader_item, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SkillIQLeader leader = leaderList.get(position);
        holder.binding.setSkillLeader(leader);
        holder.binding.executePendingBindings();
    }

    public void refreshList(List<SkillIQLeader> newList) {
        leaderList.clear();
        leaderList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return leaderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SkillLeaderItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
