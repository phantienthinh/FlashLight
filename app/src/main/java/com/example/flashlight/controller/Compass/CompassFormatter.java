package com.example.flashlight.controller.Compass;

import android.content.Context;

import com.example.flashlight.R;

public class CompassFormatter {
    private static final int[] sides = {0, 45, 90, 135, 180, 225, 270, 315, 360};
    private static String[] names = null;

    public CompassFormatter(Context context) {
        initLocalizedNames(context);
    }

    public String format(float azimuth) {
        int iAzimuth = (int) azimuth;
        int index = findClosestIndex(iAzimuth);
        return iAzimuth + "° " + names[index];
    }

    private void initLocalizedNames(Context context) {

        if (names == null) {
            names = new String[]{context.getString(R.string.compass_north),
                    context.getString(R.string.compass_northeast),
                    context.getString(R.string.compass_east),
                    context.getString(R.string.compass_southeast),
                    context.getString(R.string.compass_south),
                    context.getString(R.string.compass_southwest),
                    context.getString(R.string.compass_west),
                    context.getString(R.string.compass_northwest),
                    context.getString(R.string.compass_north)
            };
        }
    }

    private static int findClosestIndex(int target) {

        int i = 0, j = sides.length, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;

            if (target < sides[mid]) {
                if (mid > 0 && target > sides[mid - 1]) {
                    return getClosest(mid - 1, mid, target);
                }

                j = mid;
            } else {
                if (mid < sides.length - 1 && target < sides[mid + 1]) {
                    return getClosest(mid, mid + 1, target);
                }
                i = mid + 1;
            }
        }
        return mid;
    }

    private static int getClosest(int index1, int index2, int target) {
        if (target - sides[index1] >= sides[index2] - target) {
            return index2;
        }
        return index1;
    }
}