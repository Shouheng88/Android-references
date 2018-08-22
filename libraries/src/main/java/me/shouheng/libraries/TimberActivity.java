package me.shouheng.libraries;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.databinding.ActivityTimberBinding;
import timber.log.Timber;

@Route(path = BaseConstants.LIBRARY_TIMBER)
public class TimberActivity extends CommonActivity<ActivityTimberBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_timber;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().btnLogV.setOnClickListener(v -> Timber.v(getBinding().et.getText().toString()));
        getBinding().btnLogD.setOnClickListener(v -> Timber.d(getBinding().et.getText().toString()));
        getBinding().btnLogE.setOnClickListener(v -> Timber.e(getBinding().et.getText().toString()));
        getBinding().btnLogW.setOnClickListener(v -> Timber.w(getBinding().et.getText().toString()));
        getBinding().btnLogDate.setOnClickListener(v ->
                Timber.d("当前时间： %s", new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(new Date())));
    }
}
