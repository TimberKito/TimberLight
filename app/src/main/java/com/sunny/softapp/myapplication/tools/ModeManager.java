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
    private static final int shortInterval = 100;  // Adjusted short flash duration
    private static final int longInterval = 600;   // Adjusted long flash duration
    private static final int gapInterval = 200;    // Gap between flashes
    private static final int cycleInterval = 2000; // Gap between SOS cycles
    private static final int[] sosPattern = {
            1, 0, 1, 0, 1, 0, // S: short short short
            2, 0, 2, 0, 2, 0, // O: long long long
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
        Long frequencyStatus = App.getFrequencyStatus();
        startFlashLight(frequencyStatus);
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

    public static void onMode03() {
        timer = new Timer();
        onSos();
    }

    private static void onSos() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    boolean isLight = sosPattern[currentStep] != 0;
                    cameraManager.setTorchMode("0", isLight);

                    int nextInterval;
                    if (isLight) {
                        nextInterval = (sosPattern[currentStep] == 1) ? shortInterval : longInterval;
                    } else {
                        nextInterval = gapInterval;
                    }

                    currentStep = (currentStep + 1) % sosPattern.length;

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            onSos();
                        }
                    }, nextInterval);
                } catch (CameraAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0);
    }
}
