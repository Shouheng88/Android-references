package me.shouheng.advanced.remote;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.advanced.R;
import me.shouheng.advanced.databinding.ActivityRemoteBinding;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;

/**
 * @author shouh
 * @version $Id: RemoteActivity, v 0.1 2018/10/22 22:03 shouh Exp$
 */
@Route(path = BaseConstants.ADVANCED_REMOTE)
public class RemoteActivity extends CommonActivity<ActivityRemoteBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_remote;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        String content = getIntent().getStringExtra(BaseConstants.ADVANCED_REMOTE_ARG_CONTENT);
        getBinding().tvContent.setText(content);
    }
}
