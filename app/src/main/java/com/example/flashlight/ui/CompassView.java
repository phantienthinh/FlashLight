package com.example.flashlight.ui;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.flashlight.R;
import com.example.flashlight.controller.Compass.CompassFormatter;

public class CompassView extends RelativeLayout implements View.OnClickListener {

    private TextView tvCompassDirection;
    private CompassFormatter compassFormatter;
    private float currentAzimuth;
    private ImageView ivCompassArrow;
    private showQuestion listener;

    public CompassView(Context context) {
        this(context, null);
        initView(context);
    }

    public CompassView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        compassFormatter = new CompassFormatter(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CompassView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        compassFormatter = new CompassFormatter(context);
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.compass_view_layout, this);
        tvCompassDirection = view.findViewById(R.id.main_compass_direction_tv);
        ivCompassArrow = findViewById(R.id.main_compass_pointer_iv);
        findViewById(R.id.main_compass_question_iv).setOnClickListener(this);

    }

    public void setCompassDirection(float azimuth) {
        tvCompassDirection.setText(compassFormatter.format(azimuth));
        adjustArrow(azimuth);
    }

    private void adjustArrow(float azimuth) {

        Animation an = new RotateAnimation(-currentAzimuth, -azimuth,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        currentAzimuth = azimuth;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        ivCompassArrow.startAnimation(an);
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.showQuestion();
    }

    public void setListener(showQuestion listener) {
        this.listener = listener;
    }

    public interface showQuestion {
        void showQuestion();
    }
}
