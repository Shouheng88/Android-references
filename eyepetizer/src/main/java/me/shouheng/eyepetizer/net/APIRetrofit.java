package me.shouheng.eyepetizer.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author shouh
 * @version $Id: APIRetrofit, v 0.1 2018/8/19 17:29 shouh Exp$
 */
public class APIRetrofit {

    public static APIService getEyepetizerService() {
        return new Retrofit.Builder()
                .baseUrl("http://baobab.kaiyanapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build().create(APIService.class);
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }
}
