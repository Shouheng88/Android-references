package me.shouheng.references.view.live;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import me.shouheng.commons.util.ToastUtils;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityLiveBinding;
import me.shouheng.references.model.live.data.Banner;
import me.shouheng.references.view.CommonDaggerActivity;
import me.shouheng.references.view.live.adapter.RecommendAdapter;
import me.shouheng.references.viewmodel.LiveViewModel;

public class LiveActivity extends CommonDaggerActivity<ActivityLiveBinding> {

    @Inject LiveViewModel liveViewModel;

    private RecommendAdapter recommendAdapter;

    private ConvenientBanner<Banner> convenientBanner;

    private List<Banner> banners = new LinkedList<>();

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

        getBinding().rv.setLayoutManager(new LinearLayoutManager(this));
        getBinding().rv.setAdapter(recommendAdapter);
    }

    private void configBanner() {
        View view = getLayoutInflater().inflate(R.layout.layout_banner, null, false);
        convenientBanner = view.findViewById(R.id.cb);
        convenientBanner.setOnItemClickListener(position -> {
            ToastUtils.makeToast(banners.get(position).toString());
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
            Glide.with(context)
                    .load(data.getThumb())
                    .placeholder(R.drawable.live_default)
                    .error(R.drawable.live_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
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
                    recommendAdapter.setNewData(recommendResource.data.getRoom());
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
