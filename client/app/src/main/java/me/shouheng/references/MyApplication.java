package me.shouheng.references;

import com.alibaba.android.arouter.launcher.ARouter;
import com.meituan.android.walle.WalleChannelReader;
import com.umeng.commonsdk.UMConfigure;

import me.shouheng.commons.BaseApplication;
import me.shouheng.commons.config.Configs;

/**
 * @author shouh
 * @version $Id: ModuleGuokrApp, v 0.1 2018/6/6 22:30 shouh Exp$
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

        String channel = WalleChannelReader.getChannel(this);
        UMConfigure.init(this, Configs.APP_KEY, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
    }
}
