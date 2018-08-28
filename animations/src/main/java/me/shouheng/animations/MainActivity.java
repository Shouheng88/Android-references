package me.shouheng.animations;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.animations.databinding.ActivityMainBinding;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;

@Route(path = BaseConstants.ANIMATIONS_MENU)
public class MainActivity extends CommonActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().btnReveal.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.ANIMATIONS_CIRCLE_REVEAL)
                        .navigation());
    }
}
