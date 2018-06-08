package me.shouheng.commons.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by WngShhng on 2018/5/18.*/
public abstract class CommonActivity<T extends ViewDataBinding> extends ThemedActivity {

    private T binding;

    protected abstract int getLayoutResId();

    protected abstract void doCreateView(Bundle savedInstanceState);

    protected void beforeCreate() { }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeCreate();

        if (getLayoutResId() <= 0 ) {
            throw new AssertionError("Subclass must provide a valid layout resource id");
        }

        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(), null, false);

        setContentView(binding.getRoot());

        doCreateView(savedInstanceState);
    }

    protected final T getBinding() {
        return binding;
    }

    protected <VM extends ViewModel> VM getViewModel(@NonNull Class<VM> modelClass) {
        return ViewModelProviders.of(this).get(modelClass);
    }

    protected Fragment getCurrentFragment(@IdRes int resId) {
        return getSupportFragmentManager().findFragmentById(resId);
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }

    public void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(this, activity), requestCode);
    }

    /**
     * Used to call {@link #onBackPressed()} to avoid override by sub class */
    public void superOnBackPressed() {
        super.onBackPressed();
    }
}
