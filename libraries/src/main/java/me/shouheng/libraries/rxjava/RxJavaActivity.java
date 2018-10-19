package me.shouheng.libraries.rxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.FragmentHelper;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityRxjavaBinding;

/**
 * @author shouh
 * @version $Id: RxJavaActivity, v 0.1 2018/8/7 8:22 shouh Exp$
 */
@Route(path = BaseConstants.LIBRARY_RX_JAVA)
public class RxJavaActivity extends CommonActivity<ActivityRxjavaBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().btn1.setOnClickListener(v ->
                Observable.create((ObservableOnSubscribe<String>) emitter -> {
                    String str = getBinding().et.getText().toString();
                    emitter.onNext(str);
//                emitter.onError(new Exception("Exception"));
//                emitter.onComplete();
                }).subscribe(ToastUtils::makeToast));
        getBinding().btn2.setOnClickListener(v -> Observable.just(1,2,3,4).subscribe(LogUtils::d));
        getBinding().btnRxBus.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_RX_JAVA_BUS)
                        .navigation());
        addSubscription(RxMessage.class, rxMessage -> {
            ToastUtils.makeToast(rxMessage.message);
            LogUtils.d("++++++++++++++++++++++ RxJavaActivity: 接受到消息 " + rxMessage.message + "！");
        });
        FragmentHelper.replace(this,
                (Fragment) ARouter.getInstance()
                        .build(BaseConstants.LIBRARY_FRAGMENT_DEMO)
                        .withBoolean(BaseConstants.LIBRARY_FRAGMENT_DEMO_HAS_CHILD, true)
                        .navigation(),
                R.id.fragment_container);
    }
}
