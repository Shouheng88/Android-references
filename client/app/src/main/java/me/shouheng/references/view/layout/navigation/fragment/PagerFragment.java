package me.shouheng.references.view.layout.navigation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;

import me.shouheng.commons.util.ToastUtils;
import me.shouheng.references.R;
import me.shouheng.references.common.fragment.NormalTipsFragment;
import me.shouheng.references.databinding.FragmentPagerBinding;
import me.shouheng.references.view.CommonDaggerFragment;

/**
 * Created by WngShhng on 2018/6/11.
 */
public class PagerFragment extends CommonDaggerFragment<FragmentPagerBinding> {

    private NormalTipsFragment tabFragment1;
    private NormalTipsFragment tabFragment2;
    private NormalTipsFragment tabFragment3;

    public static PagerFragment newInstance() {
        Bundle args = new Bundle();
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pager;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().toolbar.setTitle(R.string.menu_item_sub_title_1);
        getBinding().toolbar.setTitleTextColor(Color.BLACK);

        tabFragment1 = NormalTipsFragment.newInstance("Tab Fragment 1",
                "This is the content for the tab fragment 1",
                false);
        tabFragment2 = NormalTipsFragment.newInstance("Tab Fragment 2",
                "This is the content for the tab fragment 2",
                false);
        tabFragment3 = NormalTipsFragment.newInstance("Tab Fragment 3",
                "This is the content for the tab fragment 3",
                false);

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

        public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
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
