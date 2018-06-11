package me.shouheng.references.view.layout;

import android.os.Bundle;

import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityNavigationBinding;
import me.shouheng.references.view.CommonDaggerActivity;

public class NavigationActivity extends CommonDaggerActivity<ActivityNavigationBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {

    }
}
