package me.shouheng.eyepetizer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.FragmentHelper;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.eyepetizer.R;
import me.shouheng.eyepetizer.databinding.ActivityContainerBinding;

@Route(path = BaseConstants.EYEPETIZER_CONTAINER)
public class ContainerActivity extends CommonActivity<ActivityContainerBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_container;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String url = intent.getStringExtra(BaseConstants.EYEPETIZER_CONTAINER_ACTION_VIDEO_EXTRA_URL);
        Fragment fragment = (Fragment) ARouter.getInstance()
                .build(BaseConstants.EYEPETIZER_VIDEO)
                .withString(BaseConstants.EYEPETIZER_VIDEO_EXTRA_URL, url)
                .withBoolean(BaseConstants.EYEPETIZER_VIDEO_EXTRA_FULLSCREEN, true)
                .navigation();
        FragmentHelper.replace(this, fragment, R.id.fragment_container);
    }
}
