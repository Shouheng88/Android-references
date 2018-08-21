package me.shouheng.libraries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.libraries.databinding.ActivityClipPictureBinding;

/**
 * Created by WngShhng on 2018/8/21.*/
@Route(path = BaseConstants.LIBRARY_CLIP_PIC)
public class ClipPictureActivity extends CommonActivity<ActivityClipPictureBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clip_picture;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.test_pic, options);
        ToastUtils.makeToast("w:" + bitmap.getWidth() + " h:" + bitmap.getHeight());
        Bitmap cliped = Bitmap.createBitmap(bitmap, 54, 217, 125, 28);
        getBinding().ivPic.setImageBitmap(cliped);
    }
}
