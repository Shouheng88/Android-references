package me.shouheng.layout.view;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityLayoutMenuBinding;

@Route(path = BaseConstants.LAYOUT_MENU)
public class LayoutMenuActivity extends CommonActivity<ActivityLayoutMenuBinding> {

    private boolean useAdapter = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_layout_menu;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        getBinding().bntNav.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_NAVIGATION)
                        .navigation());
        getBinding().bntTabbed.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_TABBED)
                        .navigation());
        getBinding().bntBottomSheet.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_BOTTOM_SHEET)
                        .navigation());
        getBinding().bntCollapse.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_COLLAPSE_BAR)
                        .navigation());
        getBinding().bntScrolling.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_SCROLLING)
                        .navigation());
        getBinding().btnDrawer.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_DRAWER)
                        .navigation());
        getBinding().btnViewSystem.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_VIEW_SYSTEM)
                        .navigation());
        getBinding().btnViewAnimate.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_VIEW_ANIMATE)
                        .navigation());
        getBinding().bntSupport28.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_SUPPORT_28)
                        .navigation());
        getBinding().btnSwipe.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_SWIPE_BACK)
                        .navigation());
        getBinding().btnAdapter.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_ADAPTER)
                        .withBoolean(BaseConstants.LAYOUT_ADAPTER_ARG_USE_ADAPTER, useAdapter = !useAdapter)
                        .navigation());
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.menu_item_title_3);
            actionBar.setSubtitle(R.string.menu_item_desc_3);
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
