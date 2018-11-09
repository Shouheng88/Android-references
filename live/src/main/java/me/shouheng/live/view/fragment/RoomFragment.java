package me.shouheng.live.view.fragment;

import androidx.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;

import java.util.Objects;

import me.shouheng.commons.BaseApplication;
import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.StringUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.tools.ViewUtils;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.live.R;
import me.shouheng.live.databinding.FragmentRoomBinding;
import me.shouheng.live.model.data.Room;
import me.shouheng.live.model.data.RoomLine;
import me.shouheng.live.viewmodel.LiveViewModel;

/**
 * @author shouh
 * @version $Id: RoomFragment, v 0.1 2018/6/9 10:59 shouh Exp$
 */
@Route(path = BaseConstants.LIVE_DETAIL_ROOM)
public class RoomFragment extends CommonFragment<FragmentRoomBinding> {

    private String uid, thumb;

    private LiveViewModel liveViewModel;

    private VideoFragment videoFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        assert getArguments() != null;
        uid = getArguments().getString(BaseConstants.LIVE_DETAIL_ROOM_EXTRA_KEY_UID);
        thumb = getArguments().getString(BaseConstants.LIVE_DETAIL_ROOM_EXTRA_KEY_THUMB);

        liveViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);

        Objects.requireNonNull(getActivity()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        configLayout();

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

    private void configLayout() {
        boolean landscape = landscape();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getBinding().rlContainer.getLayoutParams();
        lp.height = landscape ? ViewUtils.getDisplayMetrics().heightPixels :
                (int) (ViewUtils.getDisplayMetrics().widthPixels * 9.0f / 16.0f);
        lp.topMargin = landscape ? 0 : ViewUtils.getStatusBarHeight(BaseApplication.getContext());
        getBinding().rlContainer.setLayoutParams(lp);
    }

    private void configViews() {
        getBinding().ivFullScreen.setOnClickListener(v ->
                Objects.requireNonNull(getActivity()).setRequestedOrientation(landscape() ?
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));
        getBinding().ivBack.setOnClickListener(v -> onBack());
    }

    private void playUrl(String url) {
        if (videoFragment == null) {
            videoFragment = VideoFragment.newInstance(url,false);
        }
        showVideoFragment(videoFragment);
    }

    private void showRoomInfo(@Nullable Room room) {
        if (room == null) return;

        getBinding().tvTitle.setText(room.getTitle());
        getBinding().tvViews.setText(StringUtils.formatString(R.string.live_views_number, room.getView()));
        getBinding().tvAnno.setText(room.getAnnouncement());

        Glide.with(getContext()).asBitmap().load(room.getAvatar()).into(getBinding().ivAvatar);
        getBinding().tvUserName.setText(room.getNick());
        getBinding().tvFollow.setText(StringUtils.formatString(R.string.live_follows_number, room.getFollow()));
        getBinding().tvUserIntro.setText(room.getIntro());
    }

    private void showVideoFragment(Fragment videoFragment) {
        FragmentManager manager = getFragmentManager();
        assert manager != null;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.rl_video, videoFragment).commit();
    }

    private boolean landscape() {
        return Objects.requireNonNull(getActivity()).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    private void onBack() {
        if (landscape()) {
            Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean landscape = landscape();
        getBinding().ivFullScreen.setVisibility(landscape ? View.GONE : View.VISIBLE);
        configLayout();
    }

    @Override
    public void onBackPressed() {
        onBack();
    }
}
