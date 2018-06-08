package me.shouheng.references.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.shouheng.references.model.data.Resource;
import me.shouheng.references.model.live.LiveService;
import me.shouheng.references.model.live.data.Recommend;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    }

    public LiveData<Resource<Recommend>> getRecommend() {
        MutableLiveData<Resource<Recommend>> result = new MutableLiveData<>();
        liveRetrofit.create(LiveService.class)
                .getRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Recommend>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onNext(Recommend recommend) {
                        result.setValue(Resource.success(recommend));
                    }
                });
        return result;
    }
}
