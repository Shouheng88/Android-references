package me.shouheng.libraries.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityHandlerBinding;

/**
 * Created by WngShhng on 2018/11/1.
 */
@Route(path = BaseConstants.LIBRARY_HANDLER)
public class HandlerActivity extends CommonActivity<ActivityHandlerBinding> {

    private final static int SAY_HELLO = 1;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAY_HELLO:
                    LogUtils.d("Hello!");
                    break;
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_handler;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        LogUtils.d("========开始一个任务，将在2秒之后执行========");
        new Handler().postDelayed(() -> LogUtils.d("========任务开始执行========"), 2000);

        LogUtils.d("=========开始一个线程========");
        new Thread(() -> {
            try {
                // new Handler(); // 会因为该线程中没有对应的 Looper 而发生异常
                Thread.sleep(2000);
                new Handler(Looper.getMainLooper())
                        .post(() -> getBinding().tv.setText("任务执行完毕，主线程更新UI"));
//                handler.post(() -> getBinding().tv.setText("任务执行完毕，主线程更新UI"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            LogUtils.d("+++++++++" + Thread.currentThread());
            Looper.prepare();
            new Handler().post(() -> LogUtils.d("+++++++++" + Thread.currentThread()));
            Looper.loop();
        }).start();

        Message message = Message.obtain(handler);
        message.what = SAY_HELLO;
        message.sendToTarget();
        // or below
//        Message message = Message.obtain();
//        message.what = SAY_HELLO;
//        handler.sendMessage(message);

        // HandlerThread demo
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();

        // IntentService demo
        FileRecognizeTask.start(this);
    }
}
