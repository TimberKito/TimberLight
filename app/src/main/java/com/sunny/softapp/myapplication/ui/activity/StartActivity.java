package com.sunny.softapp.myapplication.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sunny.softapp.myapplication.databinding.ActivityStartBinding;

public class StartActivity extends BaseActivity {

    private ActivityStartBinding binding;
    private final long LOADING_TIME = 2000;

    @Override
    View getRootContentView() {
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    void initView() {
        super.initView();

        new CountDownTimer(LOADING_TIME, LOADING_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                intoMainActivity();
                finish();
            }
        }.start();

    }

    private void intoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
