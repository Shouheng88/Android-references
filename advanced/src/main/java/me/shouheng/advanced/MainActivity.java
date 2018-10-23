package me.shouheng.advanced;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.advanced.databinding.ActivityMainBinding;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;

/**
 * @author shouh
 * @version $Id: MainActivity, v 0.1 2018/10/22 21:36 shouh Exp$
 */
@Route(path = BaseConstants.ADVANCED_MENU)
public class MainActivity extends CommonActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().btnRemote.setOnClickListener(v ->
                ARouter.getInstance().build(BaseConstants.ADVANCED_REMOTE)
                        .withString(BaseConstants.ADVANCED_REMOTE_ARG_CONTENT, "Simple advanced content")
                        .navigation());
        getBinding().btnRemote2.setOnClickListener(v ->
                ARouter.getInstance().build(BaseConstants.ADVANCED_REMOTE2)
                        .withString(BaseConstants.ADVANCED_REMOTE2_ARG_CONTENT, "Simple advanced content 2")
                        .navigation());
    }
}
