package me.shouheng.references.view.layout.navigation;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import me.shouheng.commons.util.PalmUtils;
import me.shouheng.references.R;
import me.shouheng.references.common.fragment.NormalTipsFragment;
import me.shouheng.references.databinding.ActivityNavigationBinding;
import me.shouheng.references.view.CommonDaggerActivity;
import me.shouheng.references.view.layout.navigation.fragment.PagerFragment;

public class NavigationActivity extends CommonDaggerActivity<ActivityNavigationBinding> {

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
            switch (menuItem.getItemId()) {
                case R.id.nav_main: showFragment(FRAGMENT_KEY_PAGER);break;
                case R.id.nav_favorite: showFragment(FRAGMENT_KEY_FAVORITE);break;
                case R.id.nav_info: showFragment(FRAGMENT_KEY_INFO);break;
            }
            return true;
        });

        showFragment(FRAGMENT_KEY_PAGER);
    }

    private void initFragments(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            pagerFragment = PagerFragment.newInstance();
            favoriteFragment = NormalTipsFragment.newInstance(PalmUtils.getStringCompact(R.string.nav_bottom_item_2),
                    PalmUtils.getStringCompact(R.string.nav_bottom_desc_2),
                    true);
            infoFragment = NormalTipsFragment.newInstance(PalmUtils.getStringCompact(R.string.nav_bottom_item_3),
                    PalmUtils.getStringCompact(R.string.nav_bottom_desc_3),
                    true);
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
