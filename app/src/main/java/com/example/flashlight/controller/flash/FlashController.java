package com.example.flashlight.controller.flash;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.util.Log;

public class FlashController {
    private static final String TAG = "FlashController";

    private Camera camera;
    private Parameters params;
    private boolean isFlashOn;
    private Handler handler;
    private long timeDelay;

    public FlashController() {
        camera = Camera.open();
        handler = new Handler();
    }

    public void startFlash(long time) {
        this.timeDelay = time;
        if (time != 0) {
            handler.postDelayed(toggleFlash, timeDelay);
        } else {
            stopFlash();
            turnOnFlash();
        }
    }

    private Runnable toggleFlash = new Runnable() {
        public void run() {
            turnOnFlash();
            turnOffFlash();
            handler.postDelayed(this, timeDelay);
        }
    };

    public void stopFlash() {
        if (handler != null) {
            handler.removeCallbacks(toggleFlash);
            isFlashOn = false;
        } else {
            turnOffFlash();
        }
    }

    private void turnOnFlash() {
        if (!isFlashOn) {
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    private void turnOffFlash() {
        if (isFlashOn) {
            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    public void setTimeDelay(long timeDelay) {
        this.timeDelay = timeDelay - 100;
        stopFlash();
        startFlash(this.timeDelay);
        Log.e(TAG, "setTimeDelay: " + this.timeDelay);
    }

    private void flashSos() {
        String myString = "111000111";

        for (int x = 0; x < myString.length(); x++) {
            if (myString.charAt(x) == '1') {
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
        }
    }
}
