package me.shouheng.live.model.repository;

import java.util.concurrent.TimeUnit;

import me.shouheng.live.model.LiveService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WngShhng on 2018/7/30.*/
public class LiveRetrofit {

    public static LiveService getLiveService() {
        return new Retrofit.Builder()
                .baseUrl("http://www.quanmin.tv/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build()
                .create(LiveService.class);
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
