package me.shouheng.layout.view.navigation;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.PalmUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.common.NormalTipsFragment;
import me.shouheng.layout.databinding.ActivityNavigationBinding;
import me.shouheng.layout.view.navigation.fragment.PagerFragment;

@Route(path = BaseConstants.LAYOUT_NAVIGATION)
public class NavigationActivity extends CommonActivity<ActivityNavigationBinding> {

    private final String FRAGMENT_KEY_PAGER = "__key_fragment_pager";
    private final String FRAGMENT_KEY_FAVORITE = "__key_fragment_favorite";
    private final String FRAGMENT_KEY_INFO = "__key_fragment_info";

    private PagerFragment pagerFragment;
    private NormalTipsFragment favoriteFragment;
    private NormalTipsFragment infoFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        initFragments(savedInstanceState);

        getBinding().nav.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_main) {
                showFragment(FRAGMENT_KEY_PAGER);
            } else if (menuItem.getItemId() == R.id.nav_favorite) {
                showFragment(FRAGMENT_KEY_FAVORITE);
            } else if (menuItem.getItemId() == R.id.nav_info) {
                showFragment(FRAGMENT_KEY_INFO);
            }
            return true;
        });

        showFragment(FRAGMENT_KEY_PAGER);
    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            pagerFragment = (PagerFragment) ARouter.getInstance()
                    .build(BaseConstants.LAYOUT_PAGER_FRAGMENT)
                    .navigation();
            favoriteFragment = (NormalTipsFragment) ARouter.getInstance()
                    .build(BaseConstants.LAYOUT_NORMAL_FRAGMENT)
                    .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_TITLE, PalmUtils.getStringCompact(R.string.nav_bottom_item_2))
                    .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_CONTENT, PalmUtils.getStringCompact(R.string.nav_bottom_desc_2))
                    .withBoolean(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_SHOW_TOOLBAR, true)
                    .navigation();
            infoFragment = (NormalTipsFragment) ARouter.getInstance()
                    .build(BaseConstants.LAYOUT_NORMAL_FRAGMENT)
                    .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_TITLE, PalmUtils.getStringCompact(R.string.nav_bottom_item_3))
                    .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_CONTENT, PalmUtils.getStringCompact(R.string.nav_bottom_desc_3))
                    .withBoolean(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_SHOW_TOOLBAR, true)
                    .navigation();
        } else {
            pagerFragment = (PagerFragment) fm.getFragment(savedInstanceState, FRAGMENT_KEY_PAGER);
            favoriteFragment = (NormalTipsFragment) fm.getFragment(savedInstanceState, FRAGMENT_KEY_FAVORITE);
            infoFragment = (NormalTipsFragment) fm.getFragment(savedInstanceState, FRAGMENT_KEY_INFO);
        }

        if (!pagerFragment.isAdded()) {
            fm.beginTransaction().add(R.id.fragment_container, pagerFragment, FRAGMENT_KEY_PAGER).commit();
        }
        if (!favoriteFragment.isAdded()) {
            fm.beginTransaction().add(R.id.fragment_container, favoriteFragment, FRAGMENT_KEY_FAVORITE).commit();
        }
        if (!infoFragment.isAdded()) {
            fm.beginTransaction().add(R.id.fragment_container, infoFragment, FRAGMENT_KEY_INFO).commit();
        }
    }

    private void showFragment(String key) {
        FragmentManager fm = getSupportFragmentManager();
        switch (key) {
            case FRAGMENT_KEY_PAGER:
                fm.beginTransaction().show(pagerFragment).hide(favoriteFragment).hide(infoFragment).commit();
                break;
            case FRAGMENT_KEY_FAVORITE:
                fm.beginTransaction().show(favoriteFragment).hide(pagerFragment).hide(infoFragment).commit();
                break;
            case FRAGMENT_KEY_INFO:
                fm.beginTransaction().show(infoFragment).hide(favoriteFragment).hide(pagerFragment).commit();
                break;
        }
    }
}
