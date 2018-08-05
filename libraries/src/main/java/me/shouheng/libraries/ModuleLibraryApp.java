package me.shouheng.libraries;

import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.BaseApplication;

/**
 * @author shouh
 * @version $Id: ModuleGuokrApp, v 0.1 2018/6/6 22:30 shouh Exp$
 */
public class ModuleLibraryApp extends BaseApplication {

    private static ModuleLibraryApp application;

    public static ModuleLibraryApp getContext() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        ARouter.init(this);
    }
}
