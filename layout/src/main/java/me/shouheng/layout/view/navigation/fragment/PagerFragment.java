package me.shouheng.layout.view.navigation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.android.material.tabs.TabLayout.Tab;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Arrays;
import java.util.List;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.layout.R;
import me.shouheng.layout.common.NormalTipsFragment;
import me.shouheng.layout.databinding.FragmentPagerBinding;

/**
 * Created by WngShhng on 2018/6/11.*/
@Route(path = BaseConstants.LAYOUT_PAGER_FRAGMENT)
public class PagerFragment extends CommonFragment<FragmentPagerBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pager;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().toolbar.setTitle(R.string.menu_item_sub_title_1);
        getBinding().toolbar.setTitleTextColor(Color.BLACK);

        Fragment tabFragment1 = (NormalTipsFragment) ARouter.getInstance()
                .build(BaseConstants.LAYOUT_NORMAL_FRAGMENT)
                .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_TITLE, "Tab Fragment 1")
                .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_CONTENT, "This is the content for the tab fragment 1")
                .withBoolean(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_SHOW_TOOLBAR, false)
                .navigation();
        Fragment tabFragment2 = (NormalTipsFragment) ARouter.getInstance()
                .build(BaseConstants.LAYOUT_NORMAL_FRAGMENT)
                .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_TITLE, "Tab Fragment 2")
                .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_CONTENT, "This is the content for the tab fragment 2")
                .withBoolean(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_SHOW_TOOLBAR, false)
                .navigation();
        Fragment tabFragment3 = (NormalTipsFragment) ARouter.getInstance()
                .build(BaseConstants.LAYOUT_NORMAL_FRAGMENT)
                .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_TITLE, "Tab Fragment 3")
                .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_CONTENT, "This is the content for the tab fragment 3")
                .withBoolean(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_SHOW_TOOLBAR, false)
                .navigation();

        getBinding().vp.setAdapter(new TabFragmentAdapter(getFragmentManager(),
                Arrays.asList(tabFragment1, tabFragment2, tabFragment3),
                Arrays.asList("Tab1", "Tab2", "Tab3")));
        getBinding().vp.setOffscreenPageLimit(3);
        getBinding().tabLayout.setupWithViewPager(getBinding().vp);
        getBinding().tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                ToastUtils.makeToast("Tab" + (1 + tab.getPosition()) + "clicked!");
            }

            @Override
            public void onTabUnselected(Tab tab) { }

            @Override
            public void onTabReselected(Tab tab) { }
        });

        getBinding().fab.setOnClickListener(v -> ToastUtils.makeToast("Fab clicked!"));
    }

    public static class TabFragmentAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        private List<String> titles;

        TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
            if (fragments.size() - titles.size() != 0) {
                throw new IllegalArgumentException("The length for fragments and titles must be the same!");
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
