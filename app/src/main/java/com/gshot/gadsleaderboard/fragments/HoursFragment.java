package com.gshot.gadsleaderboard.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.gshot.gadsleaderboard.R;
import com.gshot.gadsleaderboard.Utils;
import com.gshot.gadsleaderboard.adapters.HourLeadersRecyclerAdapter;
import com.gshot.gadsleaderboard.models.HoursLeader;
import com.gshot.gadsleaderboard.persistence.RoomDatabase;
import com.gshot.gadsleaderboard.remote.RetrofitClientGADSAPI;
import com.gshot.gadsleaderboard.remote.GadsAPI;
import com.gshot.gadsleaderboard.databinding.FragmentHoursBinding;

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

public class HoursFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    FragmentHoursBinding binding;

    Retrofit service;

    RoomDatabase database;

    public HoursFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hours, container, false);
        service = RetrofitClientGADSAPI.buildService();
        database = RoomDatabase.getDatabase(getContext());
        binding.swipeRefresh.setOnRefreshListener(this);

        if (Utils.checkConnectivity(getContext())) {
            fetchHourLeaders();
        }
        else
            fetchFromDatabase();

        return binding.getRoot();
    }

    private void fetchHourLeaders() {
        GadsAPI hoursAPi = service.create(GadsAPI.class);
        binding.progressBar.setVisibility(View.VISIBLE);
        hoursAPi.getHoursLeaders().enqueue(new Callback<List<HoursLeader>>() {
            @Override
            public void onResponse(Call<List<HoursLeader>> call, Response<List<HoursLeader>> response) {
                if (response.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);
                    List<HoursLeader> hoursLeaders = Utils.sortHoursList(response.body());
                    binding.setHoursLeaders(hoursLeaders);
                    saveToDatabase(hoursLeaders);
                }
            }

            @Override
            public void onFailure(Call<List<HoursLeader>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Snackbar.make(binding.recyclerView, getString(R.string.checkConnection), BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToDatabase(List<HoursLeader> leaders) {
         new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<HoursLeader> oldList = database.getHoursDAO().getHoursLeaders();
                if (oldList.isEmpty()) {
                    database.getHoursDAO().insertHoursLeaders(leaders);
                }
                else {
                    database.getHoursDAO().deleteRecords(oldList);
                    database.getHoursDAO().insertHoursLeaders(leaders);
                }
                return null;
            }
         }.execute();
    }

    private void fetchFromDatabase() {
        new AsyncTask<Void, Void, List<HoursLeader>>() {
            @Override
            protected List<HoursLeader> doInBackground(Void... voids) {
                return database.getHoursDAO().getHoursLeaders();
            }

            @Override
            protected void onPostExecute(List<HoursLeader> leaders) {
                binding.progressBar.setVisibility(View.GONE);
                if (leaders.isEmpty()) {
                    Snackbar.make(binding.recyclerView, getString(R.string.checkConnection), BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else {
                    binding.setHoursLeaders(Utils.sortHoursList(leaders));
                }
            }

            @Override
            protected void onPreExecute() {
                binding.progressBar.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @Override
    public void onRefresh() {
        if (Utils.checkConnectivity(getContext())) {
            GadsAPI hoursAPi = service.create(GadsAPI.class);
            binding.progressBar.setVisibility(View.VISIBLE);
            hoursAPi.getHoursLeaders().enqueue(new Callback<List<HoursLeader>>() {
                @Override
                public void onResponse(Call<List<HoursLeader>> call, Response<List<HoursLeader>> response) {
                    if (response.isSuccessful()) {
                        binding.progressBar.setVisibility(View.GONE);
                        List<HoursLeader> hoursLeaders = Utils.sortHoursList(response.body());
                        HourLeadersRecyclerAdapter adapter = (HourLeadersRecyclerAdapter) binding.recyclerView.getAdapter();
                        if (adapter == null)
                            binding.setHoursLeaders(hoursLeaders);
                        else
                            adapter.refreshList(hoursLeaders);
                        saveToDatabase(hoursLeaders);
                    }
                }

                @Override
                public void onFailure(Call<List<HoursLeader>> call, Throwable t) {
                    binding.progressBar.setVisibility(View.GONE);
                    Snackbar.make(binding.recyclerView, getString(R.string.failedLoad), BaseTransientBottomBar.LENGTH_SHORT).show();
                }
            });
        }
        else
            Snackbar.make(binding.recyclerView, getString(R.string.checkConnection), BaseTransientBottomBar.LENGTH_SHORT).show();
        binding.swipeRefresh.setRefreshing(false);
    }
}
