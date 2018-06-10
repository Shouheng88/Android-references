package me.shouheng.references.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.shouheng.references.model.data.Resource;
import me.shouheng.references.model.guokr.GuokrService;
import me.shouheng.references.model.guokr.data.GuokrNews;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author shouh
 * @version $Id: GuokrViewModel, v 0.1 2018/6/10 11:10 shouh Exp$
 */
public class GuokrViewModel extends AndroidViewModel {

    @Inject GuokrService guokrService;

    @Inject
    public GuokrViewModel(@NonNull Application application, GuokrService guokrService) {
        super(application);
        this.guokrService = guokrService;
    }

    public LiveData<Resource<GuokrNews>> getGuokrNews(int offset, int limit) {
        MutableLiveData<Resource<GuokrNews>> result = new MutableLiveData<>();
        guokrService.getNews(offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GuokrNews>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onNext(GuokrNews guokrNews) {
                        result.setValue(Resource.success(guokrNews));
                    }
                });
        return result;
    }
}
