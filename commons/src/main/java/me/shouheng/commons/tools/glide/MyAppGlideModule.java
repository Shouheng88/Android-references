package me.shouheng.commons.tools.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader.Factory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import me.shouheng.commons.tools.LogUtils;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;

/**
 * @author shouh
 * @version $Id: MyAppGlideModule, v 0.1 2018/7/29 18:53 shouh Exp$
 */
@GlideModule
@Excludes(value = {com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule.class})
public class MyAppGlideModule extends AppGlideModule {

    private static final String DISK_CACHE_DIR = "Glide_cache";

    private static final long DISK_CACHE_SIZE = 100 << 20; // 100M

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_DIR, DISK_CACHE_SIZE));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .eventListener(new EventListener() {
                    @Override
                    public void callStart(Call call) {
                        // 输出日志，用于确认使用了我们配置的 OkHttp 进行网络请求
                        LogUtils.d(call.request().url().toString());
                    }
                })
                .build();
        registry.replace(GlideUrl.class, InputStream.class, new Factory(okHttpClient));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        // 不使用 Manifest 中的 GlideModule
        return false;
    }
}
