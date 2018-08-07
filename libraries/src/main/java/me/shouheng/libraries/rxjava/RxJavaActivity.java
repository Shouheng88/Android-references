package me.shouheng.libraries.rxjava;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import io.reactivex.Observable;
import me.shouheng.commons.config.BaseConstants;
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
        Observable.just(1).subscribe();
    }
}
