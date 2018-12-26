package me.shouheng.advanced;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.annotation.Nullable;
import me.shouheng.advanced.aidl.INoteManager;
import me.shouheng.advanced.aidl.Note;
import me.shouheng.advanced.aidl.NoteService;
import me.shouheng.advanced.callback.ActResultRequest;
import me.shouheng.advanced.databinding.ActivityAdvancedBinding;
import me.shouheng.advanced.keepalive.LongLiveService;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;

/**
 * @author shouh
 * @version $Id: MainActivity, v 0.1 2018/10/22 21:36 shouh Exp$
 */
@Route(path = BaseConstants.ADVANCED_MENU)
public class MainActivity extends CommonActivity<ActivityAdvancedBinding> {

    private INoteManager noteManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            noteManager = INoteManager.Stub.asInterface(service);
            try {
                Note note = noteManager.getNote(100);
                LogUtils.d(note);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_advanced;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        /* 开启远程的活动 */
        getBinding().btnRemote.setOnClickListener(v ->
                ARouter.getInstance().build(BaseConstants.ADVANCED_REMOTE)
                        .withString(BaseConstants.ADVANCED_REMOTE_ARG_CONTENT, "Simple advanced content")
                        .navigation());
        getBinding().btnRemote2.setOnClickListener(v ->
                ARouter.getInstance().build(BaseConstants.ADVANCED_REMOTE2)
                        .withString(BaseConstants.ADVANCED_REMOTE2_ARG_CONTENT, "Simple advanced content 2")
                        .navigation());

        /* 两种获取程序执行结果的效果的对比 */
        getBinding().btnResult.setOnClickListener(v -> {
            Intent intent = new Intent(this, Activity2.class);
            ActResultRequest request = new ActResultRequest(this);

            /* 实际的原理就是通过一个没有背景的 fragment 来发起请求并在其中处理请求并进行回调
             * 问题：当不保留活动的时候 GG */
            request.startForResult(intent, 1, (resultCode, data) -> {
                String result = data.getStringExtra(Activity2.EXTRA_KEY_NAME);
                ToastUtils.makeToast(result);
            });
        });
        getBinding().btnResult2.setOnClickListener(v -> {
            /* 正常的启动活动并获取结果的逻辑，即使不保留活动也正常运行 */
            Intent intent = new Intent(this, Activity2.class);
            startActivityForResult(intent, 0);
        });

        /* 跨进程获取笔记信息 */
        getBinding().btnDisplay.setOnClickListener(v -> {
            try {
                Note note = noteManager.getNote(100);
                ToastUtils.makeToast(note + "");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        /* 启动前台服务 */
        getBinding().btnFore.setOnClickListener(v -> {
            Intent intent = new Intent(this, LongLiveService.class);
            startService(intent);
        });

        /* 绑定 NoteService */
        Intent intent = new Intent(this, NoteService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                assert data != null;
                String result = data.getStringExtra(Activity2.EXTRA_KEY_NAME);
                ToastUtils.makeToast(result);
                break;
            default:
                // do nothing
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
