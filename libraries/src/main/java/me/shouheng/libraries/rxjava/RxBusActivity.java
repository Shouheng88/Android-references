package me.shouheng.libraries.rxjava;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityRxBusBinding;

/**
 * Created by WngShhng on 2018/8/17.
 */
@Route(path = BaseConstants.LIBRARY_RX_JAVA_BUS)
public class RxBusActivity extends CommonActivity<ActivityRxBusBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rx_bus;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().btnSendEvent.setOnClickListener(v -> {
            postEvent(new RxMessage("Hello world!"));
        });
    }
}
