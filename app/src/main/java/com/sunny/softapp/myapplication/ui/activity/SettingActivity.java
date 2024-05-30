package com.sunny.softapp.myapplication.ui.activity;

import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.sunny.softapp.myapplication.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySettingBinding binding;

    @Override
    View getRootContentView() {
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    void initView() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .init();
        initButton();

    }

    private void initButton() {
        binding.settingBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.settingBack) {
            finish();
        }

    }
}
