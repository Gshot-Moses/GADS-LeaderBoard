package com.gshot.gadsleaderboard.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.gshot.gadsleaderboard.R;
import com.gshot.gadsleaderboard.Utils;
import com.gshot.gadsleaderboard.adapters.SkillLeaderRecyclerAdapter;
import com.gshot.gadsleaderboard.models.SkillIQLeader;
import com.gshot.gadsleaderboard.persistence.RoomDatabase;
import com.gshot.gadsleaderboard.remote.RetrofitClientGADSAPI;
import com.gshot.gadsleaderboard.remote.GadsAPI;
import com.gshot.gadsleaderboard.databinding.FragmentSkillBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SkillFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentSkillBinding binding;

    Retrofit service;

    RoomDatabase database;

    public SkillFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skill, container, false);
        binding.swipeRefresh.setOnRefreshListener(this);

        service = RetrofitClientGADSAPI.buildService();
        database = RoomDatabase.getDatabase(getContext());

        if (Utils.checkConnectivity(getContext()))
            fetchSkillLeaders();
        else
            fetchDataLocally();

        return binding.getRoot();
    }

    private void fetchSkillLeaders() {
        GadsAPI skillApi = service.create(GadsAPI.class);
        binding.progressBar.setVisibility(View.VISIBLE);
        skillApi.getSkillIQLeaders().enqueue(new Callback<List<SkillIQLeader>>() {
            @Override
            public void onResponse(Call<List<SkillIQLeader>> call, Response<List<SkillIQLeader>> response) {
                if (response.isSuccessful()) {
                    List<SkillIQLeader> leaders = Utils.sortScoresList(response.body());
                    binding.progressBar.setVisibility(View.GONE);
                    binding.setLeadersList(leaders);
                }
            }

            @Override
            public void onFailure(Call<List<SkillIQLeader>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Snackbar.make(binding.recyclerView, getString(R.string.failedLoad), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataLocally() {
        new AsyncTask<Void, Void, List<SkillIQLeader>>() {

            @Override
            protected void onPreExecute() {
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(List<SkillIQLeader> skillIQLeaders) {
                binding.progressBar.setVisibility(View.GONE);
                if (skillIQLeaders.isEmpty()) {
                    Snackbar.make(binding.recyclerView, getString(R.string.checkConnection), BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else
                    binding.setLeadersList(skillIQLeaders);
            }

            @Override
            protected List<SkillIQLeader> doInBackground(Void... voids) {
                return database.getSkillDAO().getSkillLeaders();
            }
        }.execute();
    }

    private void saveToDatabase(List<SkillIQLeader> leaders) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<SkillIQLeader> oldList = database.getSkillDAO().getSkillLeaders();
                if (oldList.isEmpty()) {
                    database.getSkillDAO().insertSkillLeaders(leaders);
                }
                else {
                    database.getSkillDAO().deleteRecords(oldList);
                    database.getSkillDAO().insertSkillLeaders(leaders);
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void onRefresh() {
        if (Utils.checkConnectivity(getContext())) {
            GadsAPI skillApi = service.create(GadsAPI.class);
            binding.progressBar.setVisibility(View.VISIBLE);
            skillApi.getSkillIQLeaders().enqueue(new Callback<List<SkillIQLeader>>() {
                @Override
                public void onResponse(Call<List<SkillIQLeader>> call, Response<List<SkillIQLeader>> response) {
                    if (response.isSuccessful()) {
                        binding.progressBar.setVisibility(View.GONE);
                        List<SkillIQLeader> leaders = Utils.sortScoresList(response.body());
                        SkillLeaderRecyclerAdapter adapter = (SkillLeaderRecyclerAdapter) binding.recyclerView.getAdapter();
                        if (adapter == null)
                            binding.setLeadersList(leaders);
                        else
                            adapter.refreshList(leaders);
                        saveToDatabase(leaders);
                    }
                }

                @Override
                public void onFailure(Call<List<SkillIQLeader>> call, Throwable t) {
                    Snackbar.make(binding.recyclerView, getString(R.string.checkConnection), BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            });
        }
        else
            Snackbar.make(binding.recyclerView, getString(R.string.checkConnection), BaseTransientBottomBar.LENGTH_SHORT).show();
        binding.swipeRefresh.setRefreshing(false);
    }
}
