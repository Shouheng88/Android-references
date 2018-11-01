package me.shouheng.layout.view.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by WngShhng on 2018/10/29.
 */
public class AdapterUtils {

    private static float noCompactDensity;

    private static float noCompactScaledDensity;

    public static void setCustomDensity(Activity activity, Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (noCompactDensity == 0) {
            noCompactDensity = appDisplayMetrics.density;
            noCompactScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        noCompactScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() { }
            });
        }

        float targetDensity = appDisplayMetrics.widthPixels / 360;
        float targetScaledDensity = targetDensity * (noCompactScaledDensity / noCompactDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
