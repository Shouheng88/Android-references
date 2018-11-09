package me.shouheng.libraries.handler;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import me.shouheng.commons.tools.LogUtils;

/**
 * @author shouh
 * @version $Id: FileRecognizeTask, v 0.1 2018/11/4 23:40 shouh Exp$
 */
public class FileRecognizeTask extends IntentService {

    public static void start(Context context) {
        Intent intent = new Intent(context, FileRecognizeTask.class);
        context.startService(intent);
    }

    public FileRecognizeTask() {
        super("FileRecognizeTask");
    }


    @Override
    protected void onHandleIntent(@androidx.annotation.Nullable Intent intent) {
        // do something
        LogUtils.d("=========================================");
    }
}
