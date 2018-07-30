package me.shouheng.references.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import me.shouheng.commons.model.Resource;
import me.shouheng.references.model.live.data.AppStart;
import me.shouheng.references.model.live.data.Recommend;
import me.shouheng.references.model.live.data.Room;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author shouh
 * @version $Id: LiveViewModel, v 0.1 2018/6/8 22:13 shouh Exp$
 */
public class LiveViewModel extends ViewModel {



    public LiveData<Resource<Recommend>> getRecommend() {
        MutableLiveData<Resource<Recommend>> result = new MutableLiveData<>();
        liveService.getRecommend()
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

    public LiveData<Resource<AppStart>> getAppStart() {
        MutableLiveData<Resource<AppStart>> result = new MutableLiveData<>();
        liveService.getAppStartInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppStart>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onNext(AppStart appStart) {
                        result.setValue(Resource.success(appStart));
                    }
                });
        return result;
    }

    public LiveData<Resource<Room>> enterRoom(String uid) {
        MutableLiveData<Resource<Room>> result = new MutableLiveData<>();
        liveService.enterRoom(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Room>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(Resource.error(e.getMessage(), null));
                    }

                    @Override
                    public void onNext(Room room) {
                        result.setValue(Resource.success(room));
                    }
                });
        return result;
    }
}
