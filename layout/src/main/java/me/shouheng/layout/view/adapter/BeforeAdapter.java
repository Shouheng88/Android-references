package me.shouheng.layout.view.adapter;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityAdapterBinding;

/**
 * Created by WngShhng on 2018/10/29.
 */
@Route(path = BaseConstants.LAYOUT_ADAPTER)
public class BeforeAdapter extends CommonActivity<ActivityAdapterBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_adapter;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        boolean useAdapter = getIntent().getBooleanExtra(BaseConstants.LAYOUT_ADAPTER_ARG_USE_ADAPTER, false);
        LogUtils.d("should use screen adapter " + useAdapter);
        if (useAdapter) {
            AdapterUtils.setCustomDensity(this, getApplication());
        }

        new AlertDialog.Builder(this)
                .setTitle("测试对话框")
                .setMessage("用来测试布局适配性的对话框")
                .create().show();
    }
}
