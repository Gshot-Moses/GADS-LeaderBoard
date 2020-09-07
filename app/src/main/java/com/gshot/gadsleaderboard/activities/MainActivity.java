package com.gshot.gadsleaderboard.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gshot.gadsleaderboard.R;
import com.gshot.gadsleaderboard.adapters.PagerAdapter;
import com.gshot.gadsleaderboard.fragments.HoursFragment;
import com.gshot.gadsleaderboard.fragments.SkillFragment;
import com.gshot.gadsleaderboard.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.toolbarTitle.setText(getString(R.string.toolbarTitle));

        setupPagerAndTab();

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SubmitActivity.class));
            }
        });
    }

    private void setupPagerAndTab() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        HoursFragment hoursFragment = new HoursFragment();
        pagerAdapter.addFragment(hoursFragment);
        SkillFragment skillFragment = new SkillFragment();
        pagerAdapter.addFragment(skillFragment);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }
}
