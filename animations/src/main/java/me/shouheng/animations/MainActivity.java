package me.shouheng.animations;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.balysv.materialripple.MaterialRippleLayout;

import me.shouheng.animations.databinding.ActivityMainBinding;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
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
        getBinding().btnToast.setOnClickListener(v -> showToastWithIcon());
        MaterialRippleLayout.on(getBinding().tvRipple)
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .rippleColor(Color.BLUE)
                .create();
        MaterialRippleLayout.on(getBinding().tvRipple2)
                .rippleColor(Color.parseColor("#FF0000"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();
        getBinding().tvRipple.setOnClickListener(v -> ToastUtils.makeToast("Ripple1"));
        getBinding().tvRipple2.setOnClickListener(v -> ToastUtils.makeToast("Ripple2"));
    }

    private void showToastWithIcon() {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        LinearLayout ly = new LinearLayout(MainActivity.this);
        ly.setGravity(Gravity.CENTER_VERTICAL);
        ImageView iv = new ImageView(MainActivity.this);
        iv.setImageResource(R.mipmap.ic_launcher);
        TextView tv = new TextView(MainActivity.this);
        tv.setText("Test toast message");
        ly.addView(tv);
        ly.addView(iv);
        toast.setView(ly);
        toast.show();
    }
}
