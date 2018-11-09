package me.shouheng.live.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.shouheng.commons.model.Resource;
import me.shouheng.live.model.data.AppStart;
import me.shouheng.live.model.data.Recommend;
import me.shouheng.live.model.data.Room;
import me.shouheng.live.model.repository.LiveRetrofit;

/**
 * @author shouh
 * @version $Id: LiveViewModel, v 0.1 2018/6/8 22:13 shouh Exp$
 */
public class LiveViewModel extends ViewModel {

    public LiveData<Resource<Recommend>> getRecommend() {
        MutableLiveData<Resource<Recommend>> result = new MutableLiveData<>();
        LiveRetrofit.getLiveService().getRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommend>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(Recommend recommend) {
                        result.setValue(Resource.success(recommend));
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onComplete() { }
                });
        return result;
    }

    public LiveData<Resource<AppStart>> getAppStart() {
        MutableLiveData<Resource<AppStart>> result = new MutableLiveData<>();
        LiveRetrofit.getLiveService().getAppStartInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppStart>() {
                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onComplete() { }

                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(AppStart appStart) {
                        result.setValue(Resource.success(appStart));
                    }
                });
        return result;
    }

    public LiveData<Resource<Room>> enterRoom(String uid) {
        MutableLiveData<Resource<Room>> result = new MutableLiveData<>();
        LiveRetrofit.getLiveService().enterRoom(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Room>() {
                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onComplete() { }

                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onNext(Room room) {
                        result.setValue(Resource.success(room));
                    }
                });
        return result;
    }
}
