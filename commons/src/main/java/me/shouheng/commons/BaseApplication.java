package me.shouheng.commons;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;

/**
 * @author shouh
 * @version $Id: BaseApplication, v 0.1 2018/6/6 21:58 shouh Exp$
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        MultiDex.install(this);

        LeakCanary.install(this);

        ARouter.init(this);

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            ARouter.openLog();
            ARouter.openDebug();
        }
    }
}
