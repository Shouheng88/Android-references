package me.shouheng.references.view.live.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.Objects;

import javax.inject.Inject;

import me.shouheng.commons.tools.LogUtils;
import me.shouheng.commons.tools.StringUtils;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.tools.ViewUtils;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.references.MyApplication;
import me.shouheng.references.R;
import me.shouheng.references.databinding.FragmentRoomBinding;
import me.shouheng.references.model.live.data.Room;
import me.shouheng.references.model.live.data.RoomLine;
import me.shouheng.references.viewmodel.LiveViewModel;

import static me.shouheng.commons.model.Status.*;

/**
 * @author shouh
 * @version $Id: RoomFragment, v 0.1 2018/6/9 10:59 shouh Exp$
 */
public class RoomFragment extends CommonFragment<FragmentRoomBinding> {

    private final static String EXTRA_UID = "__extra_uid";

    private final static String EXTRA_THUMB = "__extra_thumb";

    private String uid, thumb;

    private LiveViewModel liveViewModel;

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
        lp.topMargin = landscape ? 0 : ViewUtils.getStatusBarHeight(MyApplication.getContext());
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
