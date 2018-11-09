package me.shouheng.commons.view.activity;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;

import dagger.android.AndroidInjection;

/**
 * @author shouh
 * @version $Id: CommonDaggerActivity, v 0.1 2018/6/6 22:46 shouh Exp$
 */
public abstract class CommonDaggerActivity<T extends ViewDataBinding> extends CommonActivity<T> {

    @Override
    protected void beforeCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
    }
}
