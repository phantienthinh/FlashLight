package com.example.flashlight.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private boolean destroyed;

    // return layout id
    @LayoutRes
    protected abstract int getLayoutResId();

    // find all view id here
    protected abstract void findViewById();

    // common_check valid activity here (like common_check file path)
    protected boolean onPreCreate() {
        return true;
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!onPreCreate())
            return;

        onCreateInit(savedInstanceState);
    }

    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        setContentView(getLayoutResId());
        findViewById();
    }

    private void changeLocale(Locale locale) {
        Configuration configuration = getResources().getConfiguration();
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public void doBack(View view) {
        onBackPressed();
    }

    protected void setNavigationBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getWindow() != null) {
            getWindow().setNavigationBarColor(color);
        }
    }

    public boolean isDestroyed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return super.isDestroyed();
        else
            return destroyed;
    }

    @Override
    public void finish() {
        destroyed = true;
        super.finish();
    }

    @Override
    protected void onDestroy() {
        destroyed = true;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        if (!fragments.isEmpty()) {
//            for (int i = fragments.size() - 1; i >= 0; i--) {
//                Fragment fra = fragments.get(i);
//                if (fra instanceof BaseFragment) {
//                    if (((BaseFragment) fra).onBackPressed())
//                        return;
//                }
//            }
//        }

        destroyed = true;
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public final void bindClick(int resId, View.OnClickListener onClickListener) {
        View view = findViewById(resId);
        if (view != null)
            view.setOnClickListener(onClickListener);
    }

    public final void bindClicks(View.OnClickListener onClickListener, int... resIds) {
        for (int id : resIds) {
            bindClick(id, onClickListener);
        }
    }

}

