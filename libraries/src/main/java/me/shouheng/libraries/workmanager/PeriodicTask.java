package me.shouheng.libraries.workmanager;

import android.content.Context;
import androidx.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import me.shouheng.commons.tools.LogUtils;

/**
 * Created by WngShhng on 2018/9/13.
 */
public class PeriodicTask extends Worker {

    private static int repeatCountStatic = 0;

    private int repeatCount = 0;

    public PeriodicTask(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

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
