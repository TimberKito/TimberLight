package com.sunny.softapp.myapplication.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sunny.softapp.myapplication.App;
import com.sunny.softapp.myapplication.R;
import com.sunny.softapp.myapplication.databinding.ActivityMainBinding;
import com.sunny.softapp.myapplication.tools.ModeManager;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 50;
    private ActivityMainBinding binding;
    protected Boolean hasCameraPermission;
    private Vibrator vibrator;

    @Override
    View getRootContentView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    void initView() {
        super.initView();

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();
            for (int i = 0; i < cameraIdList.length; i++) {
                Log.e("----", cameraIdList[i]);
            }
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraIdList[0]);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }

        requestPermission();
        initButton();
        initVibrator();
    }

    private void initVibrator() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            VibratorManager systemService = (VibratorManager) getSystemService(VIBRATOR_MANAGER_SERVICE);
            vibrator = systemService.getDefaultVibrator();
        } else {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }
    }

    private void requestPermission() {
        hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        if (!hasCameraPermission) {
            showDialog();
            hasCameraPermission = !hasCameraPermission;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Granted Permission")
                .setMessage("Please grant this application camera permission so that you can use this application.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                    }
                });
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        System.exit(0);
//                    }
//                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initButton() {
        binding.mainLight.setOnClickListener(this);
        binding.mainSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.mainLight) {
            if (hasCameraPermission) {
                if (App.getVibratorStatus()) {
                    vibrator.vibrate(100);
                }
                boolean selected = binding.mainLight.isSelected();
                if (selected) {
                    ModeManager.onOffLight();
                } else {
                    int buttonId = binding.modeRadioGroup.getCheckedRadioButtonId();
                    if (buttonId == R.id.mode1) {
                        ModeManager.onMode01();
                    } else if (buttonId == R.id.mode2) {
                        ModeManager.onMode02();
                    } else if (buttonId == R.id.mode3) {
                        ModeManager.onMode03();
                    }
                }
                binding.mainLight.setSelected(!selected);
            } else {
                showDialog();
            }
        } else if (v == binding.mainSetting) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        }
    }
}
