package me.shouheng.references.view.live.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import java.util.Objects;

import javax.inject.Inject;

import me.shouheng.commons.util.ToastUtils;
import me.shouheng.references.R;
import me.shouheng.references.databinding.FragmentRoomBinding;
import me.shouheng.references.model.live.data.Room;
import me.shouheng.references.model.live.data.RoomLine;
import me.shouheng.references.view.CommonDaggerFragment;
import me.shouheng.references.viewmodel.LiveViewModel;

/**
 * @author shouh
 * @version $Id: RoomFragment, v 0.1 2018/6/9 10:59 shouh Exp$
 */
public class RoomFragment extends CommonDaggerFragment<FragmentRoomBinding> {

    private final static String EXTRA_UID = "__extra_uid";

    private final static String EXTRA_THUMB = "__extra_thumb";

    private String uid, thumb;

    @Inject LiveViewModel liveViewModel;

    private VideoFragment videoFragment;

    public static RoomFragment newInstance(String uid, String thumb) {
        Bundle args = new Bundle();
        args.putString(EXTRA_UID, uid);
        args.putString(EXTRA_THUMB, thumb);
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        uid = getArguments().getString(EXTRA_UID);
        thumb = getArguments().getString(EXTRA_THUMB);

        Objects.requireNonNull(getActivity()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        enterRoom();
    }

    private void enterRoom() {
        liveViewModel.enterRoom(uid).observe(this, roomResource -> {
            if (roomResource == null) {
                return;
            }
            switch (roomResource.status) {
                case SUCCESS:
                    Room room = roomResource.data;
                    if(room != null){
                        String url;
                        RoomLine roomLine = room.getLive().getWs();
                        RoomLine.FlvBean flv = roomLine.getFlv();
                        if(flv != null){
                            url = flv.getValue(false).getSrc();
                        }else{
                            url = roomLine.getHls().getValue(false).getSrc();
                        }
                        playUrl(url);
                    }
                    break;
                case FAILED:
                    ToastUtils.makeToast(roomResource.message);
                    break;
            }
        });
    }

    public void playUrl(String url) {
        if (videoFragment == null) {
            videoFragment = VideoFragment.newInstance(url,false);
        }
        showVideoFragment(videoFragment);
    }

    private void showVideoFragment(Fragment videoFragment) {
        FragmentManager manager = getFragmentManager();
        assert manager != null;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rl_video, videoFragment).commit();
    }
}
