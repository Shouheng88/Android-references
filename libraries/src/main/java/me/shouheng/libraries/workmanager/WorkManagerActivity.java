package me.shouheng.libraries.workmanager;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.OneTimeWorkRequest.Builder;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityWorkManagerBinding;

/**
 * Created by WngShhng on 2018/9/13.
 */
@Route(path = BaseConstants.LIBRARY_WORK_MANAGER)
public class WorkManagerActivity extends CommonActivity<ActivityWorkManagerBinding> {

    private WorkManager workManager;
    private LiveData<List<WorkStatus>> workStatuses;
    private String TAG_OUTPUT = "tag_output";
    private String SINGLE_TASK_NAME = "SINGLE_TASK";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_work_manager;
    }

    /**
     * 任何一个任务在触发之后会按照指定的规则运行，如果后台被清理掉，那么这些任务会暂停，并被清理掉
     * 下次打开应用的时候会立即触发并执行这些任务：
     * 1.没执行完毕的单次任务会被重新执行，像下面的两个依次执行的任务，当1执行完毕，2在执行中的时候
     * 清理了后台，那么重新打开应用的时候会把2重新执行一遍，1不会被执行了（因为本身处在队列中，执行完就不在队列中了）；
     * 2.如果是连续的任务，那么当后台被清理掉，并重新打开的时候，会立即重新执行一遍任务。
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        workManager = WorkManager.getInstance();
        workStatuses = workManager.getStatusesByTag(TAG_OUTPUT);

        getBinding().btnStartSingle.setOnClickListener(v -> {
            WorkContinuation continuation = workManager.beginUniqueWork(SINGLE_TASK_NAME,
                    ExistingWorkPolicy.REPLACE,
                    OneTimeWorkRequest.from(SingleTask.class));
            // 构建一个任务
            Constraints constraints = new Constraints.Builder().build();
            OneTimeWorkRequest.Builder builder = new Builder(SingleTask2.class).setConstraints(constraints);
            // 注意赋值操作
            continuation = continuation.then(builder.build());
            // 开始后台工作
            continuation.enqueue();
        });

        getBinding().btnCancelSingle.setOnClickListener(v -> workManager.cancelUniqueWork(SINGLE_TASK_NAME));

        getBinding().btnStartPeriodic.setOnClickListener(v -> {
            PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(PeriodicTask.class, 15, TimeUnit.MINUTES).build();
            workManager.enqueue(periodicWork);
        });

        getBinding().btnCancelPeriodic.setOnClickListener(v -> {

        });
    }
}
