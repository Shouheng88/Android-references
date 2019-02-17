package me.shouheng.advanced.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import me.shouheng.commons.tools.ToastUtils;

import static me.shouheng.advanced.MainActivity.MSG_EXTRA_REPLAY_STRING;
import static me.shouheng.advanced.MainActivity.MSG_REPLAY_ID;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version $Id: MessengerService, v 0.1 2019/2/16 14:38 shouh Exp$
 */
public class MessengerService extends Service {

    public static final int MSG_SAY_SOMETHING = 11;

    public static final String MSG_EXTRA_COMMAND = "__extra_command";

    private static class MessengerHandler extends Handler {

        private WeakReference<Service> serviceRef;

        MessengerHandler(Service service) {
            this.serviceRef = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_SOMETHING:
                    ToastUtils.makeToast("远程服务收到消息，内容是：" + msg.getData().getString(MSG_EXTRA_COMMAND));
                    // 向客户端返回消息
                    Messenger client = msg.replyTo;
                    Message message = Message.obtain(null, MSG_REPLAY_ID);
                    Bundle bundle = new Bundle();
                    bundle.putString(MSG_EXTRA_REPLAY_STRING, "00000");
                    message.setData(bundle);
                    try {
                        client.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Messenger messenger = new Messenger(new MessengerHandler(this));

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ToastUtils.makeToast("MessengerService bound!");
        return messenger.getBinder();
    }
}
