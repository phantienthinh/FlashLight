package com.example.flashlight.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.example.flashlight.R;
import com.example.flashlight.controller.Compass.CompassListener;
import com.example.flashlight.controller.Compass.CompassObserver;
import com.example.flashlight.controller.flash.FlashController;
import com.example.flashlight.ui.CompassView;
import com.example.flashlight.ui.SeekbarSos;

public class MainActivity extends BaseActivity implements
        View.OnClickListener, CompassView.showQuestion, CompoundButton.OnCheckedChangeListener, SeekbarSos.OnSeekbarChange {

    private static int[] VIEW_EVENTS = {};

    private static final String TAG = "MainActivity";

    private CompassObserver compassObserver;
    private CompassView compassView;
    private FlashController flashController;
    private CheckBox checkBox;
    private SeekbarSos seekbarSos;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViewById() {
        checkBox = findViewById(R.id.main_button_on_off_cb);
        compassView = findViewById(R.id.main_compass_view);
        seekbarSos = findViewById(R.id.main_Seek_bar);
    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        super.onCreateInit(savedInstanceState);
        compassView.setListener(this);
        setupCompass();
        seekbarSos.setOnSeekbarChange(this);
        flashController = new FlashController();
        onEventClick();
    }

    private void onEventClick() {
        bindClicks(this, VIEW_EVENTS);
        checkBox.setOnCheckedChangeListener(this);
    }

    private void setupCompass() {
        compassObserver = new CompassObserver(this);
        compassObserver.setListener(getCompassListener());
    }

    private CompassListener getCompassListener() {
        return new CompassListener() {
            @Override
            public void onCompassDetected(final float azimuth) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        compassView.setCompassDirection(azimuth);
                    }
                });
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button_on_off_cb:
                flashController.startFlash(500);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startCompass();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCompass();
    }

    private void startCompass() {
        if (compassObserver != null)
            compassObserver.start();
    }

    private void stopCompass() {
        if (compassObserver != null)
            compassObserver.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showQuestion() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (flashController != null) {
            if (isChecked)
                flashController.startFlash(0);
            else flashController.stopFlash();
        }
    }

    @Override
    public void onSeekbarChange(int progress) {
        flashController.setTimeDelay(progress * 100);
    }
}
