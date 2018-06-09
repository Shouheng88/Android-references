package me.shouheng.references.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dagger.android.support.AndroidSupportInjection;
import me.shouheng.commons.fragment.CommonFragment;

/**
 * @author shouh
 * @version $Id: CommonDaggerFragment, v 0.1 2018/6/9 13:40 shouh Exp$
 */
public abstract class CommonDaggerFragment<T extends ViewDataBinding> extends CommonFragment<T> implements LifecycleRegistryOwner {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    @NonNull
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
