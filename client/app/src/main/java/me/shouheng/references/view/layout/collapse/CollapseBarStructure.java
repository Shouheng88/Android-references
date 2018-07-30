package me.shouheng.references.view.layout.collapse;

import android.graphics.Color;
import android.os.Bundle;

import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityCollapseBarStructureBinding;

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
