package me.shouheng.references.view;

import android.content.Intent;
import android.os.Bundle;

import me.shouheng.references.R;
import me.shouheng.references.base.CommonDaggerActivity;
import me.shouheng.references.databinding.ActivityMainBinding;
import me.shouheng.references.view.intro.AppIntroActivity;

public class MainActivity extends CommonDaggerActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        startActivity(new Intent(this, AppIntroActivity.class));
    }
}
