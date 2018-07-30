package me.shouheng.layout.view.scrolling;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityScrollingBinding;

@Route(path = BaseConstants.LAYOUT_SCROLLING)
public class ScrollingActivity extends CommonActivity<ActivityScrollingBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_scrolling;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().toolbar.setTitle(R.string.menu_item_sub_title_4);
        getBinding().toolbar.setTitleTextColor(Color.BLACK);

        getBinding().fab.setOnClickListener(v ->
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }
}
