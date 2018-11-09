package me.shouheng.commons.view.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.shouheng.commons.rxbus.RxBus;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.view.activity.CommonActivity;

/**
 * Created by wang shouheng on 2017/12/23. */
public abstract class CommonFragment<T extends ViewDataBinding> extends Fragment {

    private T binding;

    protected abstract int getLayoutResId();

    protected abstract void doCreateView(Bundle savedInstanceState);

    private View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView != null) {
            return rootView;
        }

        if (getLayoutResId() <= 0 ) {
            throw new AssertionError("Subclass must provide a valid layout resource id");
        }

        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);

        doCreateView(savedInstanceState);

        return rootView = binding.getRoot();
    }

    protected View getRoot() {
        return binding.getRoot();
    }

    protected final T getBinding() {
        return binding;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getActivity() != null) {
            getActivity().getWindow().setStatusBarColor(color);
        }
    }

    public void onBackPressed() {
        Activity activity = getActivity();
        if (activity instanceof CommonActivity) {
            ((CommonActivity) activity).superOnBackPressed();
        }
    }

    protected void postEvent(Object object) {
        RxBus.getRxBus().post(object);
    }

    protected <M> void addSubscription(Class<M> eventType, Consumer<M> action) {
        Disposable disposable = RxBus.getRxBus().doSubscribe(eventType, action, LogUtils::d);
        RxBus.getRxBus().addSubscription(this, disposable);
    }

    protected <M> void addSubscription(Class<M> eventType, Consumer<M> action, Consumer<Throwable> error) {
        Disposable disposable = RxBus.getRxBus().doSubscribe(eventType, action, error);
        RxBus.getRxBus().addSubscription(this, disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getRxBus().unSubscribe(this);
    }
}
