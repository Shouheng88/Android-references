package me.shouheng.libraries.eventbus;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityEventBus2Binding;

@Route(path = BaseConstants.LIBRARY_EVENT_BUS_ACTIVITY2)
public class EventBusActivity2 extends CommonActivity<ActivityEventBus2Binding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_event_bus2;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        EventBus.getDefault().register(this);

        getBinding().btnPublish.setOnClickListener(v -> publishContent());
        getBinding().btnPublishAndFinish.setOnClickListener(v -> {
            publishContent();
            finish();
        });
        getBinding().btnPublishSticky.setOnClickListener(v -> publishStickyontent());
    }

    private void publishContent() {
        String msg = getBinding().etMessage.getText().toString();
        EventBus.getDefault().post(MessageWrap.getInstance(msg));
        ToastUtils.makeToast("Published : " + msg);
    }

    private void publishStickyontent() {
        String msg = getBinding().etMessage.getText().toString();
        EventBus.getDefault().postSticky(MessageWrap.getInstance(msg));
        ToastUtils.makeToast("Published : " + msg);
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("EventBus");
            actionBar.setSubtitle("Demo activity 2");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        getBinding().tvMessage.setText(message.message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
