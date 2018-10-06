package me.shouheng.libraries.workmanager;

import android.support.annotation.NonNull;

import androidx.work.Worker;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import me.shouheng.commons.tools.LogUtils;

/**
 * Created by WngShhng on 2018/9/13.
 */
public class PeriodicTask extends Worker {

    private static int repeatCountStatic = 0;

    private int repeatCount = 0;

    @NonNull
    @Override
    public Result doWork() {
        repeatCount++;
        repeatCountStatic++;
        LogUtils.d("PeriodicTask " + repeatCount + "/" + repeatCountStatic + ". begins ......");
        LogUtils.d(Thread.currentThread());
        Disposable disposable = Observable.range(1, 10).subscribe(integer -> {
            System.out.println(Thread.currentThread());
            System.out.println("PeriodicTask " + repeatCount + "/" + repeatCountStatic + " : " + integer);
            Thread.sleep(1000);
        });
        return Result.SUCCESS;
    }
}
