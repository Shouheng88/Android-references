package me.shouheng.layout.view.swipe;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import me.shouheng.commons.config.BaseConstants;

/**
 * Created by WngShhng on 2018/10/19.
 */
@Route(path = BaseConstants.LAYOUT_SWIPE_BACK)
public class SwipeBackDemoActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_swipe_back);
    }
}
