package me.shouheng.commons.async;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

import me.shouheng.commons.BaseApplication;
import me.shouheng.commons.helper.FileHelper;
import me.shouheng.commons.listener.OnAttachingFileListener;
import me.shouheng.commons.model.AttachmentFile;
import me.shouheng.commons.util.PalmUtils;

/**
 * The async task to create attachment from uri. */
public class CreateAttachmentTask extends AsyncTask<Void, Void, AttachmentFile> {

    private WeakReference<Fragment> mFragmentWeakReference;

    private WeakReference<android.app.Fragment> mAppFragmentWeakReference;

    private WeakReference<Activity> mActivityWeakReference;

    private OnAttachingFileListener mOnAttachingFileListener;

    private Uri uri;

    public CreateAttachmentTask(Fragment mFragment, Uri uri, OnAttachingFileListener listener) {
        this.mFragmentWeakReference = new WeakReference<>(mFragment);
        this.uri = uri;
        this.mOnAttachingFileListener = listener;
    }

    public CreateAttachmentTask(android.app.Fragment mFragment, Uri uri, OnAttachingFileListener listener) {
        this.mAppFragmentWeakReference = new WeakReference<>(mFragment);
        this.uri = uri;
        this.mOnAttachingFileListener = listener;
    }

    public CreateAttachmentTask(Activity activity, Uri uri, OnAttachingFileListener listener) {
        mActivityWeakReference = new WeakReference<>(activity);
        this.uri = uri;
        this.mOnAttachingFileListener = listener;
    }

    @Override
    protected AttachmentFile doInBackground(Void... params) {
        return FileHelper.createAttachmentFromUri(BaseApplication.getContext(), uri);
    }

    @Override
    protected void onPostExecute(AttachmentFile mAttachment) {
        if ((mFragmentWeakReference != null && PalmUtils.isAlive(mFragmentWeakReference.get()))
                || (mActivityWeakReference != null && PalmUtils.isAlive(mActivityWeakReference.get()))
                || (mAppFragmentWeakReference != null && PalmUtils.isAlive(mAppFragmentWeakReference.get()))) {
            if (mAttachment != null) {
                mOnAttachingFileListener.onAttachingFileFinished(mAttachment);
            } else {
                mOnAttachingFileListener.onAttachingFileErrorOccurred(null);
            }
        } else {
            if (mAttachment != null) {
                FileHelper.delete(BaseApplication.getContext(), mAttachment.getUri().getPath());
            }
        }
    }
}