package com.sunny.softapp.myapplication.tools;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;

import com.sunny.softapp.myapplication.App;

import java.util.Timer;
import java.util.TimerTask;

public class ModeManager {

    private CameraManager cameraManager = (CameraManager)
            App.appContext.getSystemService(Context.CAMERA_SERVICE);
    private Timer timer;


}
