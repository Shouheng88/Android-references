package me.shouheng.references.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;

/**
 * Created by WngShhng on 2018/6/7. */
public class MainViewModel  extends AndroidViewModel {

    @Inject
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
