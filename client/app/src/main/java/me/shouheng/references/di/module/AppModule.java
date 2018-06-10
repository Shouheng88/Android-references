package me.shouheng.references.di.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.shouheng.references.model.guokr.GuokrService;
import me.shouheng.references.model.live.LiveService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shouh
 * @version $Id: AppModule, v 0.1 2018/6/8 22:04 shouh Exp$
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    LiveService provideLiveService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://www.quanmin.tv/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(LiveService.class);
    }

    @Provides
    @Singleton
    GuokrService provideGuokrService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://apis.guokr.com/minisite/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(GuokrService.class);
    }
}
