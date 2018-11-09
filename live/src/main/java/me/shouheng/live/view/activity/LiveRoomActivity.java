package me.shouheng.live.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.FragmentHelper;
import me.shouheng.commons.tools.theme.ThemeUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.live.R;
import me.shouheng.live.databinding.ActivityLiveRoomBinding;

/**
 * @author shouh
 * @version $Id: LiveRoomActivity, v 0.1 2018/6/9 10:32 shouh Exp$
 */
@Route(path = BaseConstants.LIVE_DETAIL)
public class LiveRoomActivity extends CommonActivity<ActivityLiveRoomBinding> {

    public enum RoomType {
        SUB_SCREEN(0),
        FULL_SCREEN(1);

        public final int id;

        RoomType(int id) {
            this.id = id;
        }

        public static RoomType getTypeById(int id) {
            for (RoomType roomType : values()) {
                if (roomType.id == id) {
                    return roomType;
                }
            }
            throw new IllegalArgumentException("Invalid room type id");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_live_room;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int roomTypeId = intent.getIntExtra(BaseConstants.LIVE_DETAIL_EXTRA_ROOM_TYPE, 0);
        RoomType roomType = RoomType.getTypeById(roomTypeId);
        String uid = intent.getStringExtra(BaseConstants.LIVE_DETAIL_EXTRA_UID);
        String thumb = intent.getStringExtra(BaseConstants.LIVE_DETAIL_EXTRA_THUMB);
        switch (roomType) {
            case SUB_SCREEN: {
                Fragment fragment = (Fragment) ARouter.getInstance()
                        .build(BaseConstants.LIVE_DETAIL_ROOM)
                        .withString(BaseConstants.LIVE_DETAIL_ROOM_EXTRA_KEY_UID, uid)
                        .withString(BaseConstants.LIVE_DETAIL_ROOM_EXTRA_KEY_THUMB, thumb)
                        .navigation();
                toFragment(fragment);
                break;
            }
            case FULL_SCREEN: {
                Fragment fragment = (Fragment) ARouter.getInstance()
                        .build(BaseConstants.LIVE_DETAIL_FULL_SCREEN)
                        .withString(BaseConstants.LIVE_DETAIL_FULL_SCREEN_EXTRA_KEY_UID, uid)
                        .withString(BaseConstants.LIVE_DETAIL_FULL_SCREEN_EXTRA_KEY_THUMB, thumb)
                        .navigation();
                toFragment(fragment);
                break;
            }
        }

        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            ThemeUtils.hideSystemUI(this);
        }
    }

    private void toFragment(Fragment fragment) {
        FragmentHelper.replace(this, fragment, R.id.fragment_container);
    }

    private Fragment getCurrentFragment() {
        return getCurrentFragment(R.id.fragment_container);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getCurrentFragment();
        if (fragment instanceof CommonFragment) {
            ((CommonFragment) fragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
