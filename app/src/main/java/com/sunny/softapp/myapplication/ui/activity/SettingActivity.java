package com.sunny.softapp.myapplication.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.sunny.softapp.myapplication.App;
import com.sunny.softapp.myapplication.R;
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
        initVersion();
        initAppStatus();
    }

    private void initAppStatus() {
        boolean vibratorStatus = App.getVibratorStatus();
//        boolean gestureStatus = App.getGestureStatus();
        binding.swVibrator.setChecked(vibratorStatus);
//        binding.swGesture.setChecked(gestureStatus);

        ColorStateList valueOf = ColorStateList.valueOf(getColor(R.color.yellow));
        binding.swVibrator.setThumbTintList(valueOf);
//        binding.swGesture.setThumbTintList(valueOf);

        binding.swVibrator.setOnCheckedChangeListener((buttonView, isChecked) -> {
            App.setVibratorStatus(isChecked);
        });

//        binding.swGesture.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            App.setGestureStatus(isChecked);
//        });

    }

    private void initVersion() {
        binding.settingVersion.setText(getAppVersion());
    }

    private String getAppVersion() {
        PackageInfo packageInfo;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageInfo = App.appContext.getPackageManager().getPackageInfo(App.appContext.getPackageName(), PackageManager.PackageInfoFlags.of(0));
            } else {
                packageInfo = App.appContext.getPackageManager().getPackageInfo(App.appContext.getPackageName(), 0);
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
