package me.shouheng.references.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

/**
 * Created by WngShhng on 2018/6/7. */
public class MainViewModel  extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    @Inject
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void log() {
        Log.d(TAG, "log: ");
    }
}
