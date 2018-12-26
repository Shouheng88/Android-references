package me.shouheng.advanced.callback;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;

/**
 * Created on 2018/12/26.
 */
public class OnActResultEventDispatcherFragment extends Fragment {

    public static final String TAG = "on_act_result_event_dispatcher";

    private SparseArray<ActResultRequest.Callback> mCallbacks = new SparseArray<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * 这里的 fragment 调用自己的 startActivityForResult 方法来获取结果
     *
     * @param intent
     * @param requestCode
     * @param callback
     */
    public void startForResult(Intent intent, int requestCode, ActResultRequest.Callback callback) {
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 当拿到结果之后从列表中找出回调并调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActResultRequest.Callback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);

        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }
    }

}