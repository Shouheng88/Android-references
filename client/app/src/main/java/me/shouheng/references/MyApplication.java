package me.shouheng.references;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;
import me.shouheng.commons.BaseApplication;
import me.shouheng.references.di.DaggerAppComponent;

/**
 * @author shouh
 * @version $Id: MyApplication, v 0.1 2018/6/6 22:30 shouh Exp$
 */
public class MyApplication extends BaseApplication implements HasActivityInjector, HasSupportFragmentInjector {

    private static MyApplication application;

    @Inject DispatchingAndroidInjector<Activity> activityInjector;

    @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;

    public static MyApplication getContext() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        application = this;

        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this);

        DaggerAppComponent.builder().application(this).build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
