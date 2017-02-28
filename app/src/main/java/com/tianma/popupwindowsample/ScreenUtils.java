package com.tianma.popupwindowsample;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author Tianma at 2017/2/28
 */

public class ScreenUtils {

    private static ScreenUtils sInstance;

    private int screenHeight;
    private int screenWidth;

    private ScreenUtils(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
    }

    private static ScreenUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ScreenUtils.class) {
                if (sInstance == null) {
                    sInstance = new ScreenUtils(context);
                }
            }
        }
        return sInstance;
    }

    public static int getScreenHeight(Context context) {
        return getInstance(context).screenHeight;
    }

    public static int getScreenWidth(Context context) {
        return getInstance(context).screenWidth;
    }

}
