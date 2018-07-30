package me.shouheng.commons.tools;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import me.shouheng.commons.BaseApplication;

/**
 * @author shouh
 * @version $Id: PalmUtils, v 0.1 2018/6/6 22:12 shouh Exp$
 */
public class PalmUtils {

    // region API version utils
    /**
     * API 17
     *
     * @return true->above API 17 */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * API 18
     *
     * @return true->above API 18 */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * API 19
     *
     * @return true->above API 19 */
    public static boolean isKitKat(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * API 21
     *
     * @return true->above API 21 */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * API 23
     *
     * @return true->above API 23 */
    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    // endregion

    // region judge alive
    public static boolean isAlive(Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    public static boolean isAlive(Fragment fragment) {
        return fragment != null && fragment.isAdded() && fragment.getActivity() != null && !fragment.getActivity().isFinishing();
    }

    public static boolean isAlive(android.app.Fragment fragment) {
        return fragment != null && fragment.isAdded() && fragment.getActivity() != null && !fragment.getActivity().isFinishing();
    }
    // endregion

    // region resources utils
    public static int getColorCompact(@ColorRes int colorRes) {
        return BaseApplication.getContext().getResources().getColor(colorRes);
    }

    public static String getStringCompact(@StringRes int stringRes) {
        return BaseApplication.getContext().getResources().getString(stringRes);
    }

    public static Drawable getDrawableCompact(@DrawableRes int drawableRes) {
        return BaseApplication.getContext().getResources().getDrawable(drawableRes);
    }
    // endregion

    // region environment
    public static String getPackageName(){
        return BaseApplication.getContext().getApplicationContext().getPackageName();
    }
    // endregion
}
