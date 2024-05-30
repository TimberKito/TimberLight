package com.sunny.softapp.myapplication.tools;

import android.content.Context;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import com.sunny.softapp.myapplication.App;

import java.util.Timer;
import java.util.TimerTask;

public class ModeManager {
    private static CameraManager cameraManager = (CameraManager)
            App.appContext.getSystemService(Context.CAMERA_SERVICE);
    private static Timer timer;


    private static int currentStep = 0;

    // Time intervals in milliseconds
    private static final int shortInterval = 200;  // short flash duration
    private static final int longInterval = 600;   // long flash duration
    private static final int gapInterval = 200;    // gap between flashes
    private static final int cycleInterval = 2000; // gap between SOS cycles

    private static final int[] sosPattern = {
            1, 0, 1, 0, 1, 0, // S: short short short
            1, 0, 1, 0, 1, 0, // O: long long long
            1, 0, 1, 0, 1, 0, // S: short short short
            0, 0, 0, 0, 0, 0  // pause before repeating
    };

    public static void onOffLight() {
        if (timer != null)
            timer.cancel();
        try {
            cameraManager.setTorchMode("0", false);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void onMode01() {
        try {
            cameraManager.setTorchMode("0", true);
        } catch (CameraAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void onMode02() {
        startFlashLight(300);
    }

    private static void startFlashLight(long i) {
        timer = new Timer();
        // Flash light on/off every i milliseconds
        timer.schedule(new TimerTask() {
            boolean isLight = false;

            @Override
            public void run() {
                isLight = !isLight;
                try {
                    cameraManager.setTorchMode("0", isLight);
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, i);
    }

    private static int getInterval(int step) {
        if (step >= sosPattern.length) {
            return cycleInterval;
        }
        return sosPattern[step] == 1 ? (step % 6 < 3 ? shortInterval : longInterval) : gapInterval;
    }

    public static void onMode03() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (currentStep < sosPattern.length) {
                        boolean isLight = sosPattern[currentStep] == 1;
                        cameraManager.setTorchMode("0", isLight);
                        currentStep++;
                    } else {
                        currentStep = 0;
                    }
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, getInterval(currentStep));
    }

}
