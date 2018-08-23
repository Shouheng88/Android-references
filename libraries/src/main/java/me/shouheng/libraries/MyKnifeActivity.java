package me.shouheng.libraries;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.knife.annotation.BindView;
import me.shouheng.knife.annotation.OnClick;
import me.shouheng.knife.api.MyKnife;
import me.shouheng.libraries.databinding.ActivityMyKnifeBinding;

/**
 * @Warn Test my knife activity, delete the annotations if you set this module as lib instead of application. */
@Route(path = BaseConstants.LIBRARY_MY_KNIFE)
public class MyKnifeActivity extends CommonActivity<ActivityMyKnifeBinding> {

    @BindView(id = R.id.tv)
    public TextView textView;

    @OnClick(ids = {R.id.btn})
    public void OnClick() {
        ToastUtils.makeToast("OnClick");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_knife;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        MyKnife.bind(this);
        textView.setText("This is MyKnife demo!");
    }
}
