package me.shouheng.references;

import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.BaseApplication;

/**
 * @author shouh
 * @version $Id: MyApplication, v 0.1 2018/6/6 22:30 shouh Exp$
 */
public class MyApplication extends BaseApplication {

    private static MyApplication application;

    public static MyApplication getContext() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        ARouter.init(this);
    }
}
