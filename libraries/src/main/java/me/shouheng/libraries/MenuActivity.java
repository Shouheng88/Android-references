package me.shouheng.libraries;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.databinding.ActivityMenuBinding;
import timber.log.Timber;

@Route(path = BaseConstants.LIBRARY_MENU)
public class MenuActivity extends CommonActivity<ActivityMenuBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_menu;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        getBinding().bntEventBus.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_EVENT_BUS_ACTIVITY1)
                        .navigation());
        getBinding().btnRxjava.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_RX_JAVA)
                        .navigation());
        getBinding().btnClip.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_CLIP_PIC)
                        .navigation());
        getBinding().btnTimber.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_TIMBER)
                        .navigation());
        getBinding().btnMyKnife.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_MY_KNIFE)
                        .navigation());
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.menu_item_title_4);
            actionBar.setSubtitle(R.string.menu_item_desc_4);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
