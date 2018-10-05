package me.shouheng.layout.view.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityViewSystemBinding;

/**
 * @author shouh
 * @version $Id: ViewSystemActivity, v 0.1 2018/10/5 13:24 shouh Exp$
 */
@Route(path = BaseConstants.LAYOUT_VIEW_SYSTEM)
public class ViewSystemActivity extends CommonActivity<ActivityViewSystemBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_system;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        dispCoordinate(null);
        getBinding().v.setOnTouchListener((v, event) -> {
            dispCoordinate(event);
            return true;
        });
        getBinding().btnTrans.setOnClickListener(v -> {
            getBinding().v.animate().translationX(5f);
            getBinding().v.animate().translationY(5f);
            dispCoordinate(null);
        });
        new Handler().postDelayed(() -> dispCoordinate(null), 1000);
    }

    private void dispCoordinate(MotionEvent event) {
        String coordinate = "View.Top:" + getBinding().v.getTop() + "\n"
                + "View.Bottom:" + getBinding().v.getBottom() + "\n"
                + "View.Left:" + getBinding().v.getLeft() + "\n"
                + "View.Right:" + getBinding().v.getRight() + "\n"
                + "View.X:" + getBinding().v.getX() + "\n"
                + "View.Y:" + getBinding().v.getY() + "\n"
                + "View.Height:" + getBinding().v.getHeight() + "\n"
                + "View.Width:" + getBinding().v.getWidth() + "\n";
        if (event != null) {
            coordinate += "MotionEvent.RawX:" + event.getRawX() + "\n"
                    + "MotionEvent.RawY:" + event.getRawY() + "\n"
                    + "MotionEvent.X:" + event.getX() + "\n"
                    + "MotionEvent.Y:" + event.getY() + "\n";
        }
        getBinding().tvCoordinate.setText(coordinate);
    }
}
