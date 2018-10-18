package me.shouheng.layout.view.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityViewSystemBinding;

/**
 * @author shouh
 * @version $Id: ViewSystemActivity, v 0.1 2018/10/5 13:24 shouh Exp$
 */
@Route(path = BaseConstants.LAYOUT_VIEW_SYSTEM)
public class ViewSystemActivity extends CommonActivity<ActivityViewSystemBinding> {

    private int lastX, lastY;

    private GestureDetector mGestureDetector;
    private VelocityTracker velocityTracker;
    private long duration, downTime;

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
//            layoutMove(event);
//            offsetMove(event);
//            lpMove(event);
//            scrollByMove(event);
            scrollToMove(event);
            return true;
        });
        getBinding().btnTrans.setOnClickListener(v -> {
            getBinding().v.animate().translationX(5f);
            getBinding().v.animate().translationY(5f);
            dispCoordinate(null);
        });
        getBinding().btnScroller.setOnClickListener(v -> scrollerMove());
        new Handler().postDelayed(() -> dispCoordinate(null), 1000);

        // GestureDetector
        mGestureDetector = new GestureDetector(getContext(), new MyOnGestureListener());
        velocityTracker = VelocityTracker.obtain();

        getBinding().vg.setOnTouchListener((v, event) -> {
            mGestureDetector.onTouchEvent(event);
            velocityTracker.addMovement(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_UP:
                    duration = System.currentTimeMillis() - downTime;
                    break;
            }
            return true;
        });
        getBinding().btnVelocity.setOnClickListener(v -> {
            velocityTracker.computeCurrentVelocity((int) duration);
            getBinding().tvVelocity.setText("X:" + velocityTracker.getXVelocity() + "\n"
                    + "Y:" + velocityTracker.getYVelocity());
        });
    }

    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            ToastUtils.makeToast("Click detected");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            LogUtils.d("Long press detected");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            LogUtils.d("Double tab detected");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            LogUtils.d("Fling detected");
            return true;
        }
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

    private void layoutMove(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX, offsetY = y - lastY;
                getBinding().v.layout(getBinding().v.getLeft() + offsetX,
                        getBinding().v.getTop() + offsetY,
                        getBinding().v.getRight() + offsetX,
                        getBinding().v.getBottom() + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    private void offsetMove(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX, offsetY = y - lastY;
                getBinding().v.offsetLeftAndRight(offsetX);
                getBinding().v.offsetTopAndBottom(offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    private void lpMove(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX, offsetY = y - lastY;
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getBinding().v.getLayoutParams();
                lp.leftMargin = getBinding().v.getLeft() + offsetX;
                lp.topMargin = getBinding().v.getTop() + offsetY;
                getBinding().v.setLayoutParams(lp);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    private void scrollByMove(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX, offsetY = y - lastY;
                ((View) getBinding().v.getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    private void scrollToMove(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX, offsetY = y - lastY;
                View parent = ((View) getBinding().v.getParent());
                parent.scrollTo(parent.getScrollX()-offsetX, parent.getScrollY()-offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    private void scrollerMove() {
        getBinding().v.smoothScrollTo(-400, -100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        velocityTracker.clear();
        velocityTracker.recycle();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
