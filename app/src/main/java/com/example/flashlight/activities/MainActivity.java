package com.example.flashlight.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.example.flashlight.R;
import com.example.flashlight.controller.Compass.CompassListener;
import com.example.flashlight.controller.Compass.CompassObserver;
import com.example.flashlight.controller.flash.FlashController;
import com.example.flashlight.ui.CompassView;

public class MainActivity extends BaseActivity implements
        View.OnClickListener, CompassView.showQuestion {

    private static int[] VIEW_EVENTS = {R.id.main_button_on_off_iv};

    private static final String TAG = "MainActivity";
    private Switch mSwitchAppState;

    private CompassObserver compassObserver;
    private CompassView compassView;
    private FlashController flashControler;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViewById() {
        compassView = findViewById(R.id.main_compass_view);
    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        super.onCreateInit(savedInstanceState);
        compassView.setListener(this);
        setupCompass();
        flashControler = new FlashController();
        onEventClick();
    }

    private void onEventClick() {
        bindClicks(this, VIEW_EVENTS);
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
            case R.id.main_button_on_off_iv:
                flashControler.open();
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
}
