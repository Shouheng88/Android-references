package me.shouheng.guokr.model.repository;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WngShhng on 2018/7/30.*/
public class GuokrRetrofit {

    public static GuokrService getGuokrService() {
        return new Retrofit.Builder()
                .baseUrl("http://apis.guokr.com/minisite/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOkHttpClient())
                .build().create(GuokrService.class);
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
