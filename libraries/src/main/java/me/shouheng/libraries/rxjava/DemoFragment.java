package me.shouheng.libraries.rxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.FragmentDemoBinding;

/**
 * Created by WngShhng on 2018/10/19.
 */
@Route(path = BaseConstants.LIBRARY_FRAGMENT_DEMO)
public class DemoFragment extends CommonFragment<FragmentDemoBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        boolean hasChild = getArguments().getBoolean(BaseConstants.LIBRARY_FRAGMENT_DEMO_HAS_CHILD);
        if (hasChild) {
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, (Fragment) ARouter.getInstance()
                            .build(BaseConstants.LIBRARY_FRAGMENT_DEMO)
                            .withBoolean(BaseConstants.LIBRARY_FRAGMENT_DEMO_HAS_CHILD, false)
                            .navigation())
                    .commit();
        }
        addSubscription(RxMessage.class, rxMessage ->
                LogUtils.d("++++++++++++++++++++++ DemoFragment(hasChild:" + hasChild + "): 接受到消息 " + rxMessage.message + "！"));
    }
}
