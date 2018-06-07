package me.shouheng.commons.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shouheng.commons.BaseApplication;
import me.shouheng.commons.R;
import me.shouheng.commons.async.CreateAttachmentTask;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.config.BaseRequestCode;
import me.shouheng.commons.listener.DefaultCompressListener;
import me.shouheng.commons.listener.OnAttachingFileListener;
import me.shouheng.commons.manager.ModelFactory;
import me.shouheng.commons.model.AttachmentFile;
import me.shouheng.commons.util.PalmUtils;
import me.shouheng.commons.util.ToastUtils;
import top.zibin.luban.Luban;

/**
 * @author shouh
 * @version $Id: AttachmentHelper, v 0.1 2018/6/6 22:21 shouh Exp$
 */
public class AttachmentHelper {

    private static String filePath;

    private final static boolean shouldCompress = true;

    // region resolve result
    public static<T extends Fragment & OnAttachingFileListener> void resolveResult(T fragment, int requestCode, Intent data) {
        switch (requestCode){
            case BaseRequestCode.REQUEST_TAKE_PHOTO:
                getPhoto(fragment.getContext(), BaseConstants.MIME_TYPE_IMAGE, new OnAttachingFileListener() {
                    @Override
                    public void onAttachingFileErrorOccurred(AttachmentFile attachment) {
                        fragment.onAttachingFileErrorOccurred(attachment);
                    }

                    @Override
                    public void onAttachingFileFinished(AttachmentFile attachment) {
                        if (PalmUtils.isAlive(fragment)) {
                            fragment.onAttachingFileFinished(attachment);
                        }
                    }
                });
                break;
            case BaseRequestCode.REQUEST_SELECT_IMAGE:
                startTask(fragment, data);
                break;
            case BaseRequestCode.REQUEST_TAKE_VIDEO:
                if (PalmUtils.isAlive(fragment)) {
                    fragment.onAttachingFileFinished(getVideo(data));
                }
                break;
            case BaseRequestCode.REQUEST_FILES:
                startTask(fragment, data);
                break;
            case BaseRequestCode.REQUEST_SKETCH:
                if (PalmUtils.isAlive(fragment)) {
                    fragment.onAttachingFileFinished(getSketch(BaseConstants.MIME_TYPE_SKETCH));
                }
                break;
        }
    }

    public static<T extends android.app.Fragment & OnAttachingFileListener> void resolveResult(T fragment, int requestCode, Intent data) {
        switch (requestCode){
            case BaseRequestCode.REQUEST_TAKE_PHOTO:
                getPhoto(fragment.getActivity(), BaseConstants.MIME_TYPE_IMAGE, new OnAttachingFileListener() {
                    @Override
                    public void onAttachingFileErrorOccurred(AttachmentFile attachment) {
                        fragment.onAttachingFileErrorOccurred(attachment);
                    }

                    @Override
                    public void onAttachingFileFinished(AttachmentFile attachment) {
                        if (PalmUtils.isAlive(fragment)) {
                            fragment.onAttachingFileFinished(attachment);
                        }
                    }
                });
                break;
            case BaseRequestCode.REQUEST_SELECT_IMAGE:
                startTask(fragment, data);
                break;
            case BaseRequestCode.REQUEST_TAKE_VIDEO:
                if (PalmUtils.isAlive(fragment)) {
                    fragment.onAttachingFileFinished(getVideo(data));
                }
                break;
            case BaseRequestCode.REQUEST_FILES:
                startTask(fragment, data);
                break;
            case BaseRequestCode.REQUEST_SKETCH:
                if (PalmUtils.isAlive(fragment)) {
                    fragment.onAttachingFileFinished(getSketch(BaseConstants.MIME_TYPE_SKETCH));
                }
                break;
        }
    }

    public static<T extends Activity & OnAttachingFileListener> void resolveResult(T activity, int requestCode, Intent data) {
        switch (requestCode){
            case BaseRequestCode.REQUEST_TAKE_PHOTO:
                getPhoto(activity, BaseConstants.MIME_TYPE_IMAGE, new OnAttachingFileListener() {
                    @Override
                    public void onAttachingFileErrorOccurred(AttachmentFile attachment) {
                        activity.onAttachingFileErrorOccurred(attachment);
                    }

                    @Override
                    public void onAttachingFileFinished(AttachmentFile attachment) {
                        if (PalmUtils.isAlive(activity)) {
                            activity.onAttachingFileFinished(attachment);
                        }
                    }
                });
                break;
            case BaseRequestCode.REQUEST_SELECT_IMAGE:
                startTask(activity, data);
                break;
            case BaseRequestCode.REQUEST_TAKE_VIDEO:
                if (PalmUtils.isAlive(activity)) {
                    activity.onAttachingFileFinished(getVideo(data));
                }
                break;
            case BaseRequestCode.REQUEST_FILES:
                startTask(activity, data);
                break;
            case BaseRequestCode.REQUEST_SKETCH:
                if (PalmUtils.isAlive(activity)) {
                    activity.onAttachingFileFinished(getSketch(BaseConstants.MIME_TYPE_SKETCH));
                }
                break;
        }
    }

    private static void getPhoto(Context context, String mimeType, OnAttachingFileListener onAttachingFileListener) {
        AttachmentFile photo = ModelFactory.getAttachment();
        photo.setMineType(mimeType);
        if (shouldCompressImage()) {
            compressImage(context, photo, new File(filePath), onAttachingFileListener);
        } else {
            photo.setPath(filePath);
            photo.setUri(FileHelper.getUriFromFile(context, new File(filePath)));
            if (onAttachingFileListener != null) {
                onAttachingFileListener.onAttachingFileFinished(photo);
            }
        }
    }

    private static boolean shouldCompressImage() {
        return shouldCompress;
    }

    private static void compressImage(Context context, AttachmentFile attachment, File var, OnAttachingFileListener onAttachingFileListener) {
        Luban.with(context)
                .load(var)
                .ignoreBy(100)
                .setTargetDir(var.getParent())
                .setCompressListener(new DefaultCompressListener() {
                    @Override
                    public void onSuccess(File file) {
                        FileHelper.delete(BaseApplication.getContext(), var.getPath());
                        attachment.setPath(file.getPath());
                        attachment.setUri(FileHelper.getUriFromFile(BaseApplication.getContext(), file));
                        if (onAttachingFileListener != null) {
                            onAttachingFileListener.onAttachingFileFinished(attachment);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (onAttachingFileListener != null) {
                            onAttachingFileListener.onAttachingFileErrorOccurred(null);
                        }
                    }
                })
                .launch();
    }

    private static AttachmentFile getSketch(String mimeType) {
        AttachmentFile photo = ModelFactory.getAttachment();
        photo.setUri(FileHelper.getUriFromFile(BaseApplication.getContext(), new File(filePath)));
        photo.setMineType(mimeType);
        photo.setPath(filePath);
        return photo;
    }

    private static AttachmentFile getVideo(Intent data) {
        AttachmentFile attachment = ModelFactory.getAttachment();
        attachment.setUri(data.getData());
        attachment.setMineType(BaseConstants.MIME_TYPE_VIDEO);
        attachment.setPath(filePath);
        return attachment;
    }

    private static <T extends Activity & OnAttachingFileListener> void startTask(T activity, Intent data) {
        for (Uri uri : getUrisFromIntent(data)) {
            new CreateAttachmentTask(activity, uri, activity).execute();
        }
    }

    private static <T extends Fragment & OnAttachingFileListener> void startTask(T fragment, Intent data) {
        for (Uri uri : getUrisFromIntent(data)) {
            new CreateAttachmentTask(fragment, uri, fragment).execute();
        }
    }

    private static <T extends android.app.Fragment & OnAttachingFileListener> void startTask(T fragment, Intent data) {
        for (Uri uri : getUrisFromIntent(data)) {
            new CreateAttachmentTask(fragment, uri, fragment).execute();
        }
    }

    private static List<Uri> getUrisFromIntent(Intent data) {
        List<Uri> uris = new ArrayList<>();
        if (PalmUtils.isJellyBean() && data.getClipData() != null) {
            for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                uris.add(data.getClipData().getItemAt(i).getUri());
            }
        } else {
            uris.add(data.getData());
        }
        return uris;
    }
    // endregion

    // region Start picking action.
    public static void pickFromAlbum(Activity activity) {
        activity.startActivityForResult(pickFromAlbum(), BaseRequestCode.REQUEST_SELECT_IMAGE);
    }

    public static void pickFromAlbum(Fragment fragment) {
        fragment.startActivityForResult(pickFromAlbum(), BaseRequestCode.REQUEST_SELECT_IMAGE);
    }

    public static void pickFromAlbum(android.app.Fragment fragment) {
        fragment.startActivityForResult(pickFromAlbum(), BaseRequestCode.REQUEST_SELECT_IMAGE);
    }

    private static Intent pickFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    public static void pickFiles(Activity activity) {
        activity.startActivityForResult(pickFiles(), BaseRequestCode.REQUEST_FILES);
    }

    public static void pickFiles(Fragment fragment) {
        fragment.startActivityForResult(pickFiles(), BaseRequestCode.REQUEST_FILES);
    }

    private static Intent pickFiles() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (PalmUtils.isJellyBeanMR2()) intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.setType("*/*");
        return intent;
    }

    public static void capture(Activity activity) {
        Intent intent = captureIntent(activity);
        if (intent == null) return;
        activity.startActivityForResult(intent, BaseRequestCode.REQUEST_TAKE_PHOTO);
    }

    public static void capture(Fragment fragment) {
        Intent intent = captureIntent(fragment.getContext());
        if (intent == null) return;
        fragment.startActivityForResult(intent, BaseRequestCode.REQUEST_TAKE_PHOTO);
    }
    // endregion

    @Nullable
    private static Intent captureIntent(Context context) {
        File file = FileHelper.createNewAttachmentFile(context, BaseConstants.MIME_TYPE_IMAGE_EXTENSION);
        if (file == null){
            ToastUtils.makeToast(R.string.failed_to_create_file);
            return null;
        }
        filePath = file.getPath();
        Uri attachmentUri = FileHelper.getUriFromFile(BaseApplication.getContext(), new File(filePath));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, attachmentUri);
        return intent;
    }
}
