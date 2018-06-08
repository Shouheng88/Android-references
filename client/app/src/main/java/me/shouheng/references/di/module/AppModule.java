package me.shouheng.references.di.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shouh
 * @version $Id: AppModule, v 0.1 2018/6/8 22:04 shouh Exp$
 */
@Module
public abstract class AppModule {

    @Provides
    @Singleton
    String provideLiveURL() {
        return "http://www.quanmin.tv/";
    }

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
    Retrofit provideLiveRetrofit(String liveURL, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(liveURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
