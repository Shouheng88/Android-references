package me.shouheng.animations;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.animations.databinding.ActivityDrawableBinding;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;

/**
 * Created by WngShhng on 2018/10/17.
 */
@Route(path = BaseConstants.ANIMATIONS_DRAWABLE)
public class DrawableActivity extends CommonActivity<ActivityDrawableBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_drawable;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {

    }
}
