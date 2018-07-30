package me.shouheng.references.view.live.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.tools.glide.GlideApp;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityLiveBinding;
import me.shouheng.references.model.live.data.Banner;
import me.shouheng.references.model.live.data.Recommend;
import me.shouheng.references.view.live.Constant;
import me.shouheng.references.view.live.adapter.RecommendAdapter;
import me.shouheng.references.viewmodel.LiveViewModel;

public class LiveActivity extends CommonActivity<ActivityLiveBinding> {

    @Inject LiveViewModel liveViewModel;

    private RecommendAdapter recommendAdapter;

    private ConvenientBanner<Banner> convenientBanner;

    private List<Banner> banners = new LinkedList<>();

    private List<Recommend.RoomBean> roomBeans = new LinkedList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_live;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        configList();

        configBanner();

        fetchAppStart();

        fetchRecommend();
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.live_title);
            actionBar.setSubtitle(R.string.live_sub_title);
        }
    }

    private void configList() {
        recommendAdapter = new RecommendAdapter(this);
        recommendAdapter.setOnRoomClickListener(this::startRoom);
        recommendAdapter.setOnItemChildClickListener(((adapter, view, position) -> {
           switch (view.getId()) {
               case R.id.tv_more:
                   // todo when click more
                   ToastUtils.makeToast("More");
                   break;
           }
        }));

        getBinding().rv.setLayoutManager(new LinearLayoutManager(this));
        getBinding().rv.setAdapter(recommendAdapter);
    }

    private void startRoom(Recommend.RoomBean.ListBean listBean) {
        LiveRoomActivity.startRoom(this,
                Constant.SHOWING.equalsIgnoreCase(listBean.getCategory_slug()) ?
                        LiveRoomActivity.RoomType.FULL_SCREEN : LiveRoomActivity.RoomType.SUB_SCREEN,
                String.valueOf(listBean.getUid()),
                listBean.getThumb());
    }

    private void configBanner() {
        View view = getLayoutInflater().inflate(R.layout.layout_banner, null, false);
        convenientBanner = view.findViewById(R.id.cb);
        convenientBanner.setOnItemClickListener(position -> {
            // todo banner event
            ToastUtils.makeToast("Banner");
        });
        convenientBanner.setPages(ImageHolder::new, banners)
                .setPageIndicator(new int[]{R.drawable.ic_dot_normal, R.drawable.ic_dot_pressed})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        if(!convenientBanner.isTurning()) {
            convenientBanner.startTurning(4000);
        }

        recommendAdapter.addHeaderView(view);
    }

    public class ImageHolder implements Holder<Banner> {

        private ImageView iv;

        @Override
        public View createView(Context context) {
            iv = new ImageView(context);
            iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return iv;
        }

        @Override
        public void UpdateUI(Context context, int position, Banner data) {
            GlideApp.with(context)
                    .load(data.getThumb())
                    .placeholder(R.drawable.live_default)
                    .error(R.drawable.live_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(iv);
        }
    }

    private void fetchRecommend() {
        liveViewModel.getRecommend().observe(this, recommendResource -> {
            if (recommendResource == null) {
                return;
            }
            switch (recommendResource.status) {
                case SUCCESS:
                    recommendAdapter.setNewData(roomBeans = recommendResource.data.getRoom());
                    break;
                case FAILED:
                    ToastUtils.makeToast(recommendResource.message);
                    break;
            }
        });
    }

    private void fetchAppStart() {
        liveViewModel.getAppStart().observe(this, appStartResource -> {
            if (appStartResource == null) {
                return;
            }
            switch (appStartResource.status) {
                case FAILED:
                    ToastUtils.makeToast(appStartResource.message);
                    break;
                case SUCCESS:
                    banners.clear();
                    banners.addAll(appStartResource.data.getBanners());
                    convenientBanner.notifyDataSetChanged();
                    break;
            }
        });
    }
}
