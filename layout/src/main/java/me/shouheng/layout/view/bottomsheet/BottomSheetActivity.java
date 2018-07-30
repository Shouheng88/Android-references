package me.shouheng.layout.view.bottomsheet;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityBottomSheetBinding;

@Route(path = BaseConstants.LAYOUT_BOTTOM_SHEET)
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
