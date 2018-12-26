package me.shouheng.advanced.aidl;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.shouheng.advanced.MainActivity;
import me.shouheng.advanced.R;
import me.shouheng.commons.tools.LogUtils;

/**
 * @author shouh
 * @version $Id: NoteService, v 0.1 2018/10/23 21:20 shouh Exp$
 */
public class NoteService extends Service {

    private CopyOnWriteArrayList<Note> notes = new CopyOnWriteArrayList<>();

    private Binder binder = new INoteManager.Stub() {
        @Override
        public Note getNote(long id) {
            return Observable.fromIterable(notes).filter(note -> note.id == id).singleOrError().blockingGet();
        }

        @Override
        public void addNote(long id, String name) {
            notes.add(new Note(id, name));
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        notes.add(new Note(100, "Note 100"));
        notes.add(new Note(101, "Note 101"));
        LogUtils.d("========== onCreate()");

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        Intent intent = new Intent(this, MainActivity.class);
        notification.contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d("========== onBind()");
        Observable.interval(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                LogUtils.d("========== " + aLong);
            }
        });
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.d("========== onRebind()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.d("========== onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("========== onDestroy()");
    }
}
