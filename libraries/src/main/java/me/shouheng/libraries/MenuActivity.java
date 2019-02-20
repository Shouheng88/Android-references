package me.shouheng.libraries;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Calendar;
import java.util.Date;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.databinding.ActivityMenuBinding;
import me.shouheng.libraries.serial.SerializeActivity;

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
        getBinding().btnFingerprint.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_FINGERPRIINT_IDENTIFY)
                        .navigation());
        getBinding().btnWorkManager.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_WORK_MANAGER)
                        .navigation());
        SerializeActivity.Monster monster = new SerializeActivity.Monster("Monster01", 1,
                new SerializeActivity.Weapon("Weapon01"), new Date(), new SerializeActivity.Grade(1));
        getBinding().btnSerial.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_SERIAL)
                        .withParcelable(BaseConstants.LIBRARY_SERIAL_ARG_MONSTER, monster)
                        .navigation());
        getBinding().btnHandler.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_HANDLER)
                        .navigation());
        getBinding().btnTimeZone.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int offset = cal.get(Calendar.ZONE_OFFSET);
            ToastUtils.makeToast(String.valueOf(offset / 3600000));
        });
    }

    public void imageCompress(View view) {
        ARouter.getInstance()
                .build(BaseConstants.LIBRARY_COMPRESS)
                .navigation();
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
