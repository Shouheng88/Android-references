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

import me.shouheng.advanced.aidl.INoteManager;
import me.shouheng.advanced.aidl.Note;
import me.shouheng.advanced.aidl.NoteService;
import me.shouheng.advanced.databinding.ActivityAdvancedBinding;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.view.activity.CommonActivity;

/**
 * @author shouh
 * @version $Id: MainActivity, v 0.1 2018/10/22 21:36 shouh Exp$
 */
@Route(path = BaseConstants.ADVANCED_MENU)
public class MainActivity extends CommonActivity<ActivityAdvancedBinding> {

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            INoteManager noteManager = INoteManager.Stub.asInterface(service);
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
        getBinding().btnRemote.setOnClickListener(v ->
                ARouter.getInstance().build(BaseConstants.ADVANCED_REMOTE)
                        .withString(BaseConstants.ADVANCED_REMOTE_ARG_CONTENT, "Simple advanced content")
                        .navigation());
        getBinding().btnRemote2.setOnClickListener(v ->
                ARouter.getInstance().build(BaseConstants.ADVANCED_REMOTE2)
                        .withString(BaseConstants.ADVANCED_REMOTE2_ARG_CONTENT, "Simple advanced content 2")
                        .navigation());
        Intent intent = new Intent(this, NoteService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
