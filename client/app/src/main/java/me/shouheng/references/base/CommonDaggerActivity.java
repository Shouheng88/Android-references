package me.shouheng.references.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dagger.android.AndroidInjection;

/**
 * @author shouh
 * @version $Id: CommonDaggerActivity, v 0.1 2018/6/6 22:46 shouh Exp$
 */
public abstract class CommonDaggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
