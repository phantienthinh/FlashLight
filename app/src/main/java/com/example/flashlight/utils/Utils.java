package com.example.flashlight.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;

import com.example.flashlight.BaseApplication;
import com.example.flashlight.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Utils {
    private static String url = "http://www.google.com";
    public static boolean sDisableFragmentAnimations = false;

    public static final int TYPE_AMR = 0;
    public static final int TYPE_WAV = 1;
    public static final int TYPE_AAC = 2;
    public static final int TYPE_MP3 = 3;

    public static final int RECORF_ALL = 0;
    public static final int ONLY_CONTACTS = 1;
    public static final int IGNORE_CONTACTS = 2;
    public static final int SPECIAL_LIST = 3;

    public static int dp2px(float dp) {
        return (int) (BaseApplication.get().getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public static boolean isCallPhonePermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    public static void showPrivacyPolicy(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void openPermissionSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

}
