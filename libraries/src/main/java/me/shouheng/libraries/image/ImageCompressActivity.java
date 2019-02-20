package me.shouheng.libraries.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.PalmUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.R;
import me.shouheng.libraries.databinding.ActivityImageCompressBinding;

@Route(path = BaseConstants.LIBRARY_COMPRESS)
public class ImageCompressActivity extends CommonActivity<ActivityImageCompressBinding> {

    private static final String TAG = "ImageCompressActivity";

    private static final String COMPRESS_FILE_NAME = "compress.jpg";
    private static final String SCALE_COMPRESS_FILE_NAME = "scale_compress.jpg";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_image_compress;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        // empty
    }

    /**
     * 临近采样图片压缩
     *
     * @param view v
     * @throws IOException exception
     */
    public void compressAlgorithm(View view) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), COMPRESS_FILE_NAME);
        StringBuilder sb = new StringBuilder(PalmUtils.getStringCompact(R.string.libraries_compress_compress));
        sb.append("：\n");

        BitmapFactory.Options options = getOriginalOptions(R.raw.ice_land);
        sb.append(String.format(PalmUtils.getStringCompact(R.string.libraries_compress_compress_original), options.outWidth, options.outHeight));
        sb.append("\n");

        // 执行图片压缩：采样率 2，压缩的质量 75
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        options.inScaled = false; // 需要设置这个参数，那么要根据资源的类型进行判断吗？
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.ice_land, options);
        Log.d(TAG, options.outWidth + " " + options.outHeight); // 从这里看的结果确实是加载了一半
        Log.d(TAG, bitmap.getWidth() + " " + bitmap.getHeight()); // TODO Error!
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
        Log.d(TAG, bitmap.getWidth() + " " + bitmap.getHeight());
        bitmap.recycle();
        saveToFileSystem(file, stream); // 最终写出的数据有问题

        ToastUtils.makeToast("Success saved to " + file.getPath());
        BitmapFactory.Options retOptions = getOriginalOptions(file.getPath());
        sb.append(String.format(PalmUtils.getStringCompact(R.string.libraries_compress_compress_result), retOptions.outWidth, retOptions.outHeight));
        getBinding().tvDisplay.setText(sb.toString());
    }

    /**
     * 双线性采样
     *
     * @param view view
     * @throws IOException IOException
     */
    public void scaleCompressAlgorithm(View view) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), SCALE_COMPRESS_FILE_NAME);
        StringBuilder sb = new StringBuilder(PalmUtils.getStringCompact(R.string.libraries_compress_scale));
        sb.append("：\n");

        BitmapFactory.Options options = getOriginalOptions(R.raw.ice_land);
        int originalWidth = options.outWidth, originalHeight = options.outHeight;
        sb.append(String.format(PalmUtils.getStringCompact(R.string.libraries_compress_compress_original), options.outWidth, options.outHeight));
        sb.append("\n");

        options.inJustDecodeBounds = false;
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.ice_land, options);
        Log.d(TAG, options.outWidth + " " + options.outHeight);
        Matrix matrix = new Matrix();
        float scale = 1200f / originalWidth;
        matrix.setScale(scale, scale);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true);
        saveToFileSystem(file, scaledBitmap);

        ToastUtils.makeToast("Success saved to " + file.getPath());
        BitmapFactory.Options retOptions = getOriginalOptions(file.getPath());
        sb.append(String.format(PalmUtils.getStringCompact(R.string.libraries_compress_compress_result), retOptions.outWidth, retOptions.outHeight));
        getBinding().tvDisplay.setText(sb.toString());
    }

    // region private methods
    private BitmapFactory.Options getOriginalOptions(int resId) {
        BitmapFactory.Options options = new Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;
        BitmapFactory.decodeResource(getResources(), resId, options);
        return options;
    }

    private BitmapFactory.Options getOriginalOptions(String saveFilePath) {
        BitmapFactory.Options retOptions = new Options();
        retOptions.inJustDecodeBounds = true;
        retOptions.inSampleSize = 1;
        BitmapFactory.decodeFile(saveFilePath, retOptions);
        return retOptions;
    }

    private void saveToFileSystem(File file, ByteArrayOutputStream stream) throws IOException {
        OutputStream fos = new FileOutputStream(file);
        fos.write(stream.toByteArray());
        fos.flush();
        fos.close();
        stream.close();
    }

    private void saveToFileSystem(File file, Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        saveToFileSystem(file, stream);
    }

    private Bitmap rotatingImage(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    // endregion

}
