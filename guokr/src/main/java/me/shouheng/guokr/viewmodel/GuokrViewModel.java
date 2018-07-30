package me.shouheng.guokr.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import me.shouheng.commons.model.Resource;
import me.shouheng.guokr.model.repository.GuokrRetrofit;
import me.shouheng.guokr.model.data.GuokrNews;
import me.shouheng.guokr.model.data.GuokrNewsContent;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author shouh
 * @version $Id: GuokrViewModel, v 0.1 2018/6/10 11:10 shouh Exp$
 */
public class GuokrViewModel extends ViewModel {

    public LiveData<Resource<GuokrNews>> getGuokrNews(int offset, int limit) {
        MutableLiveData<Resource<GuokrNews>> result = new MutableLiveData<>();
        GuokrRetrofit.getGuokrService().getNews(offset, limit)
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

    public LiveData<Resource<GuokrNewsContent>> getGuokrNewsContent(int id) {
        MutableLiveData<Resource<GuokrNewsContent>> result = new MutableLiveData<>();
        GuokrRetrofit.getGuokrService().getGuokrContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GuokrNewsContent>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onNext(GuokrNewsContent guokrNewsContent) {
                        result.setValue(Resource.success(guokrNewsContent));
                    }
                });
        return result;
    }
}
