package me.shouheng.libraries.eventbus;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityEventBus1Binding;

@Route(path = BaseConstants.LIBRARY_EVENT_BUS_ACTIVITY1)
public class EventBusActivity1 extends CommonActivity<ActivityEventBus1Binding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_event_bus1;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        getBinding().btnReg.setOnClickListener(v ->
                EventBus.getDefault().register(this));
        getBinding().btnNav2.setOnClickListener( v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_EVENT_BUS_ACTIVITY2)
                        .navigation());
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("EventBus");
            actionBar.setSubtitle("Demo activity 1");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        getBinding().tvMessage.setText(message.message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetStickyEvent(MessageWrap message) {
        String txt = "Sticky event: " + message.message;
        getBinding().tvStickyMessage.setText(txt);
    }
}
