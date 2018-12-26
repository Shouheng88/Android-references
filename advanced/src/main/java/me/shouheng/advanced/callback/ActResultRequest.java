package me.shouheng.advanced.callback;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Created on 2018/12/26.
 */
public class ActResultRequest {

    private OnActResultEventDispatcherFragment fragment;

    /**
     * new 一个 ActResultRequest 的时候会初始化一个没有背景的 Fragment
     *
     * @param activity
     */
    public ActResultRequest(AppCompatActivity activity) {
        fragment = getEventDispatchFragment(activity);
    }

    private OnActResultEventDispatcherFragment getEventDispatchFragment(AppCompatActivity activity) {
        final FragmentManager fragmentManager = activity.getSupportFragmentManager();

        OnActResultEventDispatcherFragment fragment = findEventDispatchFragment(fragmentManager);
        if (fragment == null) {
            fragment = new OnActResultEventDispatcherFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, OnActResultEventDispatcherFragment.TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    private OnActResultEventDispatcherFragment findEventDispatchFragment(FragmentManager manager) {
        return (OnActResultEventDispatcherFragment) manager.findFragmentByTag(OnActResultEventDispatcherFragment.TAG);
    }

    /**
     * 当调用 startForResult 的时候，实际上是调用 fragment 的 startForResult 方法
     *
     * @param intent
     * @param requestCode
     * @param callback
     */
    public void startForResult(Intent intent, int requestCode, Callback callback) {
        fragment.startForResult(intent, requestCode, callback);
    }

    public interface Callback {
        void onActivityResult(int resultCode, Intent data);
    }
}
