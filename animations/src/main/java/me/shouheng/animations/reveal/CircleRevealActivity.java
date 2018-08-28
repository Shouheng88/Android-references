package me.shouheng.animations.reveal;

import android.animation.Animator;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;

import io.codetail.animation.ViewAnimationUtils;
import me.shouheng.animations.R;
import me.shouheng.animations.databinding.ActivityCircleRevealBinding;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;

@Route(path = BaseConstants.ANIMATIONS_CIRCLE_REVEAL)
public class CircleRevealActivity extends CommonActivity<ActivityCircleRevealBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_circle_reveal;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().btn1.setOnClickListener(v -> animate());
    }

    private void animate() {
        int cx = (getBinding().vTop.getLeft() + getBinding().vTop.getRight()) / 2;
        int cy = (getBinding().vTop.getTop() + getBinding().vTop.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, getBinding().vTop.getWidth() - cx);
        int dy = Math.max(cy, getBinding().vTop.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        // Android native animator
        Animator animator = ViewAnimationUtils.createCircularReveal(getBinding().vTop, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1500);
        animator.start();
    }
}
