package me.shouheng.layout.view.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * @author shouh
 * @version $Id: CustomView, v 0.1 2018/10/5 16:34 shouh Exp$
 */
public class CustomView extends View {

    private Scroller scroller = new Scroller(getContext());

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int descX, int descY) {
        scroller.startScroll(getScrollX(), getScrollY(), descX - getScrollX(), descY - getScrollY(), 2000);
        invalidate();
    }
}
