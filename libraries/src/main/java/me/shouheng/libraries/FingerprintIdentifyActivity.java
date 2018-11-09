//package me.shouheng.libraries;
//
//import android.os.Bundle;
//
//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
//import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;
//
//import me.shouheng.commons.config.BaseConstants;
//import me.shouheng.commons.view.activity.CommonActivity;
//import me.shouheng.libraries.databinding.ActivityFingerprintIdentifyBinding;
//
///**
// * @author shouh
// * @version $Id: FingerprintIdentifyActivity, v 0.1 2018/8/25 14:45 shouh Exp$
// */
//@Route(path = BaseConstants.LIBRARY_FINGERPRIINT_IDENTIFY)
//public class FingerprintIdentifyActivity extends CommonActivity<ActivityFingerprintIdentifyBinding> {
//
//    private FingerprintIdentify mFingerprintIdentify;
//
//    private static final int MAX_AVAILABLE_TIMES = 5;
//
//    @Override
//    protected int getLayoutResId() {
//        return R.layout.activity_fingerprint_identify;
//    }
//
//    @Override
//    protected void doCreateView(Bundle savedInstanceState) {
//        long time = System.currentTimeMillis();
//        mFingerprintIdentify = new FingerprintIdentify(getApplicationContext(), exception ->
//                append("\nException：" + exception.getLocalizedMessage()));
//        append("\ninitialize cost " + (System.currentTimeMillis() - time) + " ms");
//        append("\nisHardwareEnable() " + mFingerprintIdentify.isHardwareEnable());
//        append("\nisRegisteredFingerprint() " + mFingerprintIdentify.isRegisteredFingerprint());
//        append("\nisFingerprintEnable() " + mFingerprintIdentify.isFingerprintEnable());
//
//        if (!mFingerprintIdentify.isFingerprintEnable()) {
//            append("\nfingerprint not support");
//            return;
//        }
//        append("\nCLICK IDENTIFY BUTTON TO START!");
//
//        getBinding().btnIdentify.setOnClickListener(v -> startIdentify());
//    }
//
//    private void startIdentify() {
//        append("\nIDENTIFY START!");
//        mFingerprintIdentify.startIdentify(MAX_AVAILABLE_TIMES, new BaseFingerprint.FingerprintIdentifyListener() {
//            @Override
//            public void onSucceed() {
//                append("\nonSucceed");
//            }
//
//            @Override
//            public void onNotMatch(int availableTimes) {
//                append("\nonNotMatch : availableTimes = " + availableTimes);
//            }
//
//            @Override
//            public void onFailed(boolean isDeviceLocked) {
//                append("\nonFailed : isDeviceLocked = " + isDeviceLocked);
//            }
//
//            @Override
//            public void onStartFailedByDeviceLocked() {
//                append("\nonStartFailedByDeviceLocked");
//            }
//        });
//    }
//
//    private void append(String msg) {
//        getBinding().tvResult.append(msg);
//    }
//}
