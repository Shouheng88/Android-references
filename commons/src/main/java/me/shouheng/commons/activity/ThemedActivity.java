package me.shouheng.commons.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;

import me.shouheng.commons.util.ThemeUtils;

/**
 * Created by WngShhng on 2018/6/7.*/
public abstract class ThemedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.customStatusBar(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setStatusBarColor(@ColorInt int color) {
        ThemeUtils.setStatusBarColor(this, color);
    }
}
