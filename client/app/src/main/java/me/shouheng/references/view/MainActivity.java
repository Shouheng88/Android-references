package me.shouheng.references.view;

import android.os.Bundle;

import me.shouheng.references.R;
import me.shouheng.references.base.CommonDaggerActivity;
import me.shouheng.references.databinding.ActivityMainBinding;

public class MainActivity extends CommonDaggerActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {

    }
}
