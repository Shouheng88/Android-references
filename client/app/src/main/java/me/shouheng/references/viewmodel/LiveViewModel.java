package me.shouheng.references.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.shouheng.commons.util.LogUtils;
import retrofit2.Retrofit;

/**
 * @author shouh
 * @version $Id: LiveViewModel, v 0.1 2018/6/8 22:13 shouh Exp$
 */
public class LiveViewModel extends AndroidViewModel {

    private Retrofit liveRetrofit;

    @Inject
    public LiveViewModel(@NonNull Application application, Retrofit liveRetrofit) {
        super(application);
        this.liveRetrofit = liveRetrofit;
        LogUtils.d(liveRetrofit);
    }

    public void say() {
        System.out.print("say what?");
    }
}
