package me.shouheng.references.view.live;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import me.shouheng.commons.activity.CommonActivity;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityLiveBinding;

public class LiveActivity extends CommonActivity<ActivityLiveBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_live;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.live_title);
            actionBar.setSubtitle(R.string.live_sub_title);
        }
    }
}
