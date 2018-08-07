package me.shouheng.libraries.rxjava;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityRxjavaBinding;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

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


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                LogUtils.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("onError : " + e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.d("onNext : " + s);
            }
        };

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                LogUtils.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("onError : " + e);
            }

            @Override
            public void onNext(String s) {
                LogUtils.d("onNext : " + s);
            }
        };

        Observable observable = Observable.create()
    }
}
