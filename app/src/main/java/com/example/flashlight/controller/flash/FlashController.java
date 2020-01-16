package com.example.flashlight.controller.flash;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class FlashController {

    private Camera camera;
    private Parameters params;
    private boolean isFlashOn;

    public void startCameraTime(final long time) {
        isFlashOn = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isFlashOn) {
                    open();
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    close();
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopCameraTime() {
        isFlashOn = false;
    }

    public void open() {
        if (!isFlashOn) {

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

    public void close() {
        if (isFlashOn) {

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

        }
    }

}
