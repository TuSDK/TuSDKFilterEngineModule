package org.lasque.tusdkdemohelper.tusdk.newUI.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * TuSDK
 * org.lasque.tusdkdemohelper.tusdk.newUI.base
 * qiniu-PLDroidMediaStreamingDemo
 *
 * @author H.ys
 * @Date 2020/8/10  15:57
 * @Copyright (c) 2020 tusdk.com. All rights reserved.
 */
public class DisplayUtils {

    private static float sNoncompatDensity = 0f;
    private static float sNoncompatScaledDensity = 0f;

    public static void setCustomDensity(Activity activity, final Application application){
        DisplayMetrics metrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0f){
            sNoncompatDensity = metrics.density;
            sNoncompatScaledDensity = metrics.scaledDensity;

            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0){
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
            float targetDensity = metrics.widthPixels / 375f;

            float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
            int targetDensityDpi = (int) (160 * targetDensity);

            metrics.density = targetDensity;
            metrics.scaledDensity = targetScaleDensity;
            metrics.densityDpi = targetDensityDpi;

            DisplayMetrics activityMetrics = activity.getResources().getDisplayMetrics();
            activityMetrics.density = targetDensity;
            activityMetrics.scaledDensity = targetScaleDensity;
            activityMetrics.densityDpi = targetDensityDpi;
        }
    }
}
