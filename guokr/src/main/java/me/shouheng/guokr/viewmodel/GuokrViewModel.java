package me.shouheng.guokr.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.shouheng.commons.model.Resource;
import me.shouheng.guokr.model.data.GuokrNews;
import me.shouheng.guokr.model.data.GuokrNewsContent;
import me.shouheng.guokr.model.repository.GuokrRetrofit;

/**
 * @author shouh
 * @version $Id: GuokrViewModel, v 0.1 2018/6/10 11:10 shouh Exp$
 */
public class GuokrViewModel extends ViewModel {

    private int offset = 0;

    private final int limit = 20;

    private MutableLiveData<Resource<GuokrNews>> guokrNewsLiveData;

    private MutableLiveData<Resource<GuokrNewsContent>> guokrNewsContentLiveData;

    public LiveData<Resource<GuokrNews>> getGuokrNewsLiveData() {
        if (guokrNewsLiveData == null) {
            guokrNewsLiveData = new MutableLiveData<>();
        }
        return guokrNewsLiveData;
    }

    public LiveData<Resource<GuokrNewsContent>> getGuokrNewsContentLiveData() {
        if (guokrNewsContentLiveData == null) {
            guokrNewsContentLiveData = new MutableLiveData<>();
        }
        return guokrNewsContentLiveData;
    }

    public void fetchFirstPage() {
        fetchGuokrNews(offset, limit);
    }

    public void fetchNextPage() {
        offset += limit;
        fetchGuokrNews(offset, limit);
    }

    private void fetchGuokrNews(int offset, int limit) {
        offset += limit;
        GuokrRetrofit.getGuokrService().getNews(offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GuokrNews>() {
                    @Override
                    public void onError(Throwable e) {
                        guokrNewsLiveData.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onComplete() { }

                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(GuokrNews guokrNews) {
                        guokrNewsLiveData.setValue(Resource.success(guokrNews));
                    }
                });
    }

    public void fetchGuokrNewsContent(int id) {
        GuokrRetrofit.getGuokrService().getGuokrContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GuokrNewsContent>() {
                    @Override
                    public void onError(Throwable e) {
                        guokrNewsContentLiveData.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onComplete() { }

                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(GuokrNewsContent guokrNewsContent) {
                        guokrNewsContentLiveData.setValue(Resource.success(guokrNewsContent));
                    }
                });
    }
}
