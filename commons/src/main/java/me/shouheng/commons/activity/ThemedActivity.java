package me.shouheng.commons.activity;

import android.os.Bundle;

import me.shouheng.commons.util.ThemeUtils;

/**
 * Created by WngShhng on 2018/6/7.*/
public abstract class ThemedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.customStatusBar(this);
    }
}
