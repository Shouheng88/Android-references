package me.shouheng.layout.view.support28;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivitySupport28Binding;

/**
 * Created by WngShhng on 2018/9/11.
 */
@Route(path = BaseConstants.LAYOUT_SUPPORT_28)
public class Support28Activity extends CommonActivity<ActivitySupport28Binding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_support_28;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().btnBottomBar.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LAYOUT_BOTTOM_APP_BAR)
                        .navigation());
    }
}
