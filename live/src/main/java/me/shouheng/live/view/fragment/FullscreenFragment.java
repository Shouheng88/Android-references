package me.shouheng.live.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Objects;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.StringUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.tools.glide.GlideApp;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.live.R;
import me.shouheng.live.databinding.FragmentFullscreenBinding;
import me.shouheng.live.model.data.Room;
import me.shouheng.live.model.data.RoomLine;
import me.shouheng.live.viewmodel.LiveViewModel;

/**
 * @author shouh
 * @version $Id: FullscreenFragment, v 0.1 2018/6/9 20:00 shouh Exp$
 */
@Route(path = BaseConstants.LIVE_DETAIL_FULL_SCREEN)
public class FullscreenFragment extends CommonFragment<FragmentFullscreenBinding> {

    private String uid, thumb;

    private LiveViewModel liveViewModel;

    private VideoFragment videoFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_fullscreen;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        uid = getArguments().getString(BaseConstants.LIVE_DETAIL_FULL_SCREEN_EXTRA_KEY_UID);
        thumb = getArguments().getString(BaseConstants.LIVE_DETAIL_FULL_SCREEN_EXTRA_KEY_THUMB);

        liveViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);

        Objects.requireNonNull(getActivity()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Glide.with(getActivity())
                .asBitmap()
                .load(thumb)
                .into(getBinding().ivCover);

        enterRoom();

        configViews();
    }

    private void enterRoom() {
        liveViewModel.enterRoom(uid).observe(this, roomResource -> {
            if (roomResource == null) {
                return;
            }
            switch (roomResource.status) {
                case SUCCESS:
                    Room room = roomResource.data;
                    LogUtils.d(room);
                    showRoomInfo(room);
                    if(room != null){
                        String url;
                        RoomLine roomLine = room.getLive().getWs();
                        RoomLine.FlvBean flv = roomLine.getFlv();
                        if (flv != null) {
                            url = flv.getValue(false).getSrc();
                        } else {
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

    private void playUrl(String url) {
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

    private void showRoomInfo(@Nullable Room room) {
        if (room == null) return;

        GlideApp.with(this).load(room.getAvatar())
                .placeholder(R.drawable.mine_default_avatar)
                .error(R.drawable.mine_default_avatar).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(getBinding().ivAvatar);
        getBinding().tvName.setText(room.getNick());
        getBinding().tvFollow.setText(StringUtils.formatString(R.string.live_follows_number, room.getFollow()));

        getBinding().tvAccount.setText(String.format(getString(R.string.live_qm_account), uid));
    }

    private void configViews() {
        getBinding().ivBack.setOnClickListener(v -> onBack());
        getBinding().ivGift.setOnClickListener(v -> getBinding().ftl.addHeart());
    }

    private void onBack() {
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onBackPressed() {
        onBack();
    }
}
