package me.shouheng.advanced;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import me.shouheng.advanced.databinding.Activity2Binding;
import me.shouheng.commons.view.activity.CommonActivity;

/**
 * Created on 2018/12/26.
 */
public class Activity2 extends CommonActivity<Activity2Binding> {

    public static final String EXTRA_KEY_NAME = "EXTRA";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_2;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().cfrm.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_KEY_NAME, getBinding().et.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
