package me.shouheng.live;

import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.BaseApplication;

/**
 * @author shouh
 * @version $Id: LiveModuleApp, v 0.1 2018/7/30 20:40 shouh Exp$
 */
public class LiveModuleApp extends BaseApplication {

    private static LiveModuleApp application;

    public static LiveModuleApp getContext() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        ARouter.init(this);
    }
}