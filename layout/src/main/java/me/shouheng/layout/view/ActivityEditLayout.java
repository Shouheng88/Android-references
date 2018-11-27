package me.shouheng.layout.view;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ViewUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityEditLayoutBinding;

/**
 * Created on 2018/11/20.
 */
@Route(path = BaseConstants.LAYOUT_EDIT_LAYOUT)
public class ActivityEditLayout extends CommonActivity<ActivityEditLayoutBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_layout;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        /* Get navigation bar height. */
        int navHeight = ViewUtils.getNavigationBarHeight(this);
        int statusBarHeight = ViewUtils.getStatusBarHeight(this);

        /* RootView visible display frame. */
        Rect visibleRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleRect);

        /* LL visible display frame. */
        Rect llVisibleRect = new Rect();
        getBinding().ll.getWindowVisibleDisplayFrame(llVisibleRect);

        /* Get window information. */
        Point p = ViewUtils.getWindowSize(this);

        /* Add a view with the same height of status bar. */
        View view = new View(this);
        view.setBackgroundColor(Color.BLUE);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        view.setLayoutParams(lp);
        getBinding().container.addView(view, 0);

        new Handler().postDelayed(() -> {
            /* Get the ll hit rectangle. */
            Rect llHitRect = new Rect();
            getBinding().ll.getHitRect(llHitRect);

            /* RootView visible display frame. */
            Rect rootHitRect = new Rect();
            getWindow().getDecorView().getHitRect(rootHitRect);

            /* Display information. */
            getBinding().tvInfo.setText("DecorView HitRect : " + rootHitRect + "\n"
                    + "DecorView VisibleRect : " +  visibleRect + "\n"
                    + "Screen : " + p + "\n"
                    + "LL HitRect : " + llHitRect + "\n"
                    + "LL VisibleRect : " + llVisibleRect + "\n"
                    + "StatusBar :" + statusBarHeight + "\n"
                    + "NavBar :" + navHeight + "\n"
                    + "LL(x:" + getBinding().ll.getX() + ", " + "y:" + getBinding().ll.getY() + "," + "w:" + getBinding().ll.getWidth() + "," + "h:" + getBinding().ll.getHeight() + ") \n"
                    + "V(x:" + getBinding().v.getX() + ", " + "y:" + getBinding().v.getY() + "," + "w:" + getBinding().v.getWidth() + "," + "h:" + getBinding().v.getHeight() + ") \n");
        }, 100);
    }
}
