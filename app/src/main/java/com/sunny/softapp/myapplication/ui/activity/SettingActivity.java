package com.sunny.softapp.myapplication.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.gyf.immersionbar.ImmersionBar;
import com.sunny.softapp.myapplication.App;
import com.sunny.softapp.myapplication.R;
import com.sunny.softapp.myapplication.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySettingBinding binding;
    private final Long[] data = {200L, 500L, 700L, 900L, 1200L};

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
        initVersion();
        initVibratorStatus();
        initFrequencyStatus();
    }

    private void initFrequencyStatus() {
        ArrayAdapter<Long> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(mAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Long selectedItem = (Long) parent.getItemAtPosition(position);
                App.setFrequencyStatus(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initVibratorStatus() {
        boolean vibratorStatus = App.getVibratorStatus();
        binding.swVibrator.setChecked(vibratorStatus);
        ColorStateList valueOf = ColorStateList.valueOf(getColor(R.color.yellow));
        binding.swVibrator.setThumbTintList(valueOf);
        binding.swVibrator.setOnCheckedChangeListener((buttonView, isChecked) -> {
            App.setVibratorStatus(isChecked);
        });
    }

    private void initVersion() {
        binding.settingVersion.setText(getAppVersion());
    }

    private String getAppVersion() {
        PackageInfo packageInfo;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageInfo = App.appContext.getPackageManager().
                        getPackageInfo(App.appContext.getPackageName(), PackageManager.PackageInfoFlags.of(0));
            } else {
                packageInfo = App.appContext.getPackageManager().
                        getPackageInfo(App.appContext.getPackageName(), 0);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
        return "Version:" + packageInfo.versionName;
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
