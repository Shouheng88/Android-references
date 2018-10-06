package me.shouheng.layout.view.views;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.animation.AnimationUtils;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Date;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityViewAnimateBinding;

/**
 * @author shouh
 * @version $Id: ViewAnimateActivity, v 0.1 2018/10/5 18:41 shouh Exp$
 */
@Route(path = BaseConstants.LAYOUT_VIEW_ANIMATE)
public class ViewAnimateActivity extends CommonActivity<ActivityViewAnimateBinding> {

    private AnimationDrawable animDraw;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_animate;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Explode());
        } else {
            overridePendingTransition(R.anim.shake, R.anim.shake);
        }

        getBinding().v.setOnClickListener(v -> ToastUtils.makeToast("Clicked"));
        getBinding().btnAniamte.setOnClickListener(v -> animate());
        getBinding().btnShake.setOnClickListener(v -> shake());

        // Drawable 动画
        getBinding().ivRecord.setImageResource(R.drawable.record_anim);
        animDraw = (AnimationDrawable) getBinding().ivRecord.getDrawable();
        animDraw.start();

        ValueAnimator animator = ValueAnimator.ofObject(new DateEvaluator(), new Date(0), new Date());
        animator.setDuration(5000);
        animator.addUpdateListener(animation -> {
            Date date = (Date) animation.getAnimatedValue();
            LogUtils.d(date);
        });
        animator.start();
    }

    private static class DateEvaluator implements TypeEvaluator<Date> {

        @Override
        public Date evaluate(float fraction, Date startValue, Date endValue) {
            long startTime = startValue.getTime();
            return new Date((long) (startTime + fraction * (endValue.getTime() - startTime)));
        }
    }

    private void animate() {
        getBinding().v.animate()
                .scaleY(0.5f)
                .scaleX(0.5f)
                .translationX(100)
                .translationX(100)
                .rotation(5f)
                .start();
    }

    private void shake() {
        getBinding().v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    @Override
    protected void onPause() {
        super.onPause();
        animDraw.stop();
    }
}
