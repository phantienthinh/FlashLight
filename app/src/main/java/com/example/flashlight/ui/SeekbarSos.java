package com.example.flashlight.ui;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;

import com.example.flashlight.R;

public class SeekbarSos extends RelativeLayout implements SeekBar.OnSeekBarChangeListener {

    private SeekBar seekBar;
    private OnSeekbarChange onSeekbarChange;

    public SeekbarSos(Context context) {
        this(context, null);
    }

    public SeekbarSos(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekbarSos(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SeekbarSos(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.seekbar_view, this);
        seekBar = view.findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser && onSeekbarChange != null) {
            onSeekbarChange.onSeekbarChange(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public interface OnSeekbarChange {
        void onSeekbarChange(int progress);
    }

    public void setOnSeekbarChange(OnSeekbarChange onSeekbarChange) {
        this.onSeekbarChange = onSeekbarChange;
    }
}
