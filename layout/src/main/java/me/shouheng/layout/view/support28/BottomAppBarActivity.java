package me.shouheng.layout.view.support28;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityBottomAppBarBinding;

/**
 * Created by WngShhng on 2018/9/11.
 */
@Route(path = BaseConstants.LAYOUT_BOTTOM_APP_BAR)
public class BottomAppBarActivity extends CommonActivity<ActivityBottomAppBarBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bottom_app_bar;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().bottomAppBar.replaceMenu(R.menu.bottom_navigation);

        List<String> items = new LinkedList<>();
        Disposable disposable = Observable.range(40, 50).subscribe(integer -> items.add(String.valueOf(integer)));
        Adapter adapter = new Adapter(items);
        getBinding().rv.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().rv.setAdapter(adapter);
    }

    private static class Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

        Adapter(@Nullable List<String> data) {
            super(android.R.layout.simple_list_item_1, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(android.R.id.text1, item);
        }
    }
}
