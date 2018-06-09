package me.shouheng.references.view.live.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import me.shouheng.commons.fragment.CommonFragment;
import me.shouheng.commons.helper.FragmentHelper;
import me.shouheng.commons.util.ThemeUtils;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityLiveRoomBinding;
import me.shouheng.references.view.CommonDaggerActivity;
import me.shouheng.references.view.live.fragment.RoomFragment;

/**
 * @author shouh
 * @version $Id: LiveRoomActivity, v 0.1 2018/6/9 10:32 shouh Exp$
 */
public class LiveRoomActivity extends CommonDaggerActivity<ActivityLiveRoomBinding> {

    private final static String EXTRA_ROOM_TYPE = "__extra_room_type";
    private final static String EXTRA_UID = "__extra_uid";
    private final static String EXTRA_THUMB = "__extra_thumb";

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

    public static void startRoom(Activity activity, RoomType roomType, String uid, String thumb) {
        Intent intent = new Intent(activity, LiveRoomActivity.class);
        intent.putExtra(EXTRA_ROOM_TYPE, roomType.id);
        intent.putExtra(EXTRA_UID, uid);
        intent.putExtra(EXTRA_THUMB, thumb);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_live_room;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int roomTypeId = intent.getIntExtra(EXTRA_ROOM_TYPE, 0);
        RoomType roomType = RoomType.getTypeById(roomTypeId);
        String uid = intent.getStringExtra(EXTRA_UID);
        String thumb = intent.getStringExtra(EXTRA_THUMB);
        switch (roomType) {
            case SUB_SCREEN:
                toFragment(RoomFragment.newInstance(uid, thumb));
                break;
            case FULL_SCREEN:
                break;
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
        }
    }
}
