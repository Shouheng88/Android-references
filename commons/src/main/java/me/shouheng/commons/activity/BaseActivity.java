package me.shouheng.commons.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import me.shouheng.commons.R;
import me.shouheng.commons.listener.OnGetPermissionCallback;
import me.shouheng.commons.util.PalmUtils;
import me.shouheng.commons.util.PermissionUtils;
import me.shouheng.commons.util.ToastUtils;

/**
 * Created by WngShhng on 2018/6/7.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private OnGetPermissionCallback onGetPermissionCallback;

    public void setOnGetPermissionCallback(OnGetPermissionCallback onGetPermissionCallback) {
        this.onGetPermissionCallback = onGetPermissionCallback;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (onGetPermissionCallback != null){
                onGetPermissionCallback.onGetPermission();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Add array length check logic to avoid ArrayIndexOutOfBoundsException
                if (permissions.length > 0 && !shouldShowRequestPermissionRationale(permissions[0])){
                    showPermissionSettingDialog(requestCode);
                } else {
                    ToastUtils.makeToast(getToastMessage(requestCode));
                }
            } else {
                ToastUtils.makeToast(getToastMessage(requestCode));
            }
        }
    }

    private void showPermissionSettingDialog(int requestCode) {
        String permissionName = PermissionUtils.getPermissionName(this, requestCode);
        String msg = String.format(getString(R.string.set_permission_in_setting), permissionName);
        new AlertDialog.Builder(this)
                .setTitle(R.string.setting_permission)
                .setMessage(msg)
                .setPositiveButton(R.string.to_set, (dialog, which) -> toSetPermission())
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    private void toSetPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", PalmUtils.getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    /**
     * Get the permission toast message according to request code. If the permission name can be found,
     * we will show the permission name in the message, otherwise show the default message.
     *
     * @param requestCode the request code
     * @return the message to toast */
    private String getToastMessage(int requestCode) {
        String permissionName = PermissionUtils.getPermissionName(this, requestCode);
        String defName = getString(R.string.permission_default_permission_name);
        if (defName.equals(permissionName)) {
            return getString(R.string.permission_denied_try_again_after_set);
        } else {
            return String.format(getString(R.string.permission_denied_try_again_after_set_given_permission), permissionName);
        }
    }

    public BaseActivity getContext() {
        return this;
    }
}

