package me.shouheng.layout.view.collapse;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityCollapseBarStructureBinding;

@Route(path = BaseConstants.LAYOUT_COLLAPSE_BAR)
public class CollapseBarStructure extends CommonActivity<ActivityCollapseBarStructureBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collapse_bar_structure;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().toolbar.setTitle("");
        getBinding().toolbar.setTitleTextColor(Color.BLACK);
    }
}
