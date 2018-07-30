package me.shouheng.references.view.layout.bottomsheet;

import android.graphics.Color;
import android.os.Bundle;

import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityBottomSheetBinding;

public class BottomSheetActivity extends CommonActivity<ActivityBottomSheetBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bottom_sheet;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().toolbar.setTitle(R.string.menu_item_sub_title_3);
        getBinding().toolbar.setTitleTextColor(Color.BLACK);
    }
}
