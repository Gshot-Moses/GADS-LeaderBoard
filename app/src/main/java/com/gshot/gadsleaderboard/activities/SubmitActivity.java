package com.gshot.gadsleaderboard.activities;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.gshot.gadsleaderboard.R;
import com.gshot.gadsleaderboard.Utils;
import com.gshot.gadsleaderboard.databinding.ActivitySubmitBinding;
import com.gshot.gadsleaderboard.remote.GoogleFormService;
import com.gshot.gadsleaderboard.remote.SubmissionRequest;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubmitActivity extends AppCompatActivity {

    ActivitySubmitBinding binding;

    Retrofit retrofit;
    SubmissionRequest request;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_submit);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        retrofit = GoogleFormService.buildService();
        request = retrofit.create(SubmissionRequest.class);

        binding.submitBtn.setOnClickListener(v -> {
            if (Utils.checkConnectivity(SubmitActivity.this)) {
                String firstName = binding.firstName.getText().toString();
                String lastName = binding.lastName.getText().toString();
                String email = binding.firstName.getText().toString();
                String link = binding.firstName.getText().toString();

                if (TextUtils.isEmpty(firstName)) {
                    Snackbar.make(binding.linearLayout, getString(R.string.supplyFirstName), BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(lastName)) {
                    Snackbar.make(binding.linearLayout, getString(R.string.supplyLastName), BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Snackbar.make(binding.linearLayout, getString(R.string.supplyEmailAddress), BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(link)) {
                    Snackbar.make(binding.linearLayout, getString(R.string.supplyLink), BaseTransientBottomBar.LENGTH_SHORT).show();
                    return;
                }

                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText(getString(R.string.confirmSend));
                sweetAlertDialog.setConfirmText("OK");
                sweetAlertDialog.setConfirmClickListener((SweetAlertDialog alertDialog) -> {
                    alertDialog.dismissWithAnimation();
                    makePostRequest(firstName, lastName, email, link);
                }).show();
            }
            else
                Snackbar.make(binding.linearLayout, getString(R.string.checkConnection), BaseTransientBottomBar.LENGTH_SHORT).show();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void makePostRequest(String firstName, String lastName, String email, String link) {
        SweetAlertDialog alertDialog = showLoadingDialog();
        alertDialog.show();
        request.submitProject(firstName, lastName, email, link).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    alertDialog.dismissWithAnimation();
                    showSuccessDialog();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                alertDialog.dismissWithAnimation();
                showFailedDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private SweetAlertDialog showLoadingDialog() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(getColor(R.color.colorAccent));
        sweetAlertDialog.setTitleText("Loading");
        sweetAlertDialog.setCancelable(false);
        return sweetAlertDialog;
    }

    private void showSuccessDialog() {
        SweetAlertDialog alertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        alertDialog.setTitleText(getString(R.string.succes));
        alertDialog.show();
    }

    private void showFailedDialog() {
        SweetAlertDialog alertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        alertDialog.setTitleText(getString(R.string.failed));
        alertDialog.show();
    }
}
