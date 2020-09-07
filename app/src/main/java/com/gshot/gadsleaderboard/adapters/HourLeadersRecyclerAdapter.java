package com.gshot.gadsleaderboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gshot.gadsleaderboard.R;
import com.gshot.gadsleaderboard.models.HoursLeader;
import com.gshot.gadsleaderboard.databinding.HoursLeaderItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HourLeadersRecyclerAdapter extends RecyclerView.Adapter<HourLeadersRecyclerAdapter.ViewHolder> {

    List<HoursLeader> leaderList;
    Context context;

    public HourLeadersRecyclerAdapter(Context context, List<HoursLeader> leaderList) {
        this.context = context;
        this.leaderList = leaderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        HoursLeaderItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.hours_leader_item, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoursLeader hoursLeader = leaderList.get(position);
        holder.hoursBinding.setHoursLeader(hoursLeader);
        holder.hoursBinding.executePendingBindings();
    }

    public void refreshList(List<HoursLeader> newList) {
        leaderList.clear();
        leaderList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return leaderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        HoursLeaderItemBinding hoursBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hoursBinding = DataBindingUtil.bind(itemView);
        }
    }
}
