package me.shouheng.references.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.shouheng.guokr.view.fragment.NewsDetailFragment;
import me.shouheng.guokr.view.fragment.NewsListFragment;
import me.shouheng.references.common.fragment.NormalTipsFragment;
import me.shouheng.references.view.layout.navigation.fragment.PagerFragment;
import me.shouheng.references.view.live.fragment.FullscreenFragment;
import me.shouheng.references.view.live.fragment.RoomFragment;
import me.shouheng.references.view.live.fragment.VideoFragment;

/**
 * @author shouh
 * @version $Id: FragmentModule, v 0.1 2018/6/9 13:45 shouh Exp$
 */
@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract RoomFragment contributeRoomFragment();

    @ContributesAndroidInjector
    abstract VideoFragment contributeVideoFragment();

    @ContributesAndroidInjector
    abstract FullscreenFragment contributeFullscreenFragment();

    @ContributesAndroidInjector
    abstract NewsListFragment contributeNewsListFragment();

    @ContributesAndroidInjector
    abstract NewsDetailFragment contributeNewsDetailFragment();

    @ContributesAndroidInjector
    abstract NormalTipsFragment contributeNormalTipsFragment();

    @ContributesAndroidInjector
    abstract PagerFragment contributePagerFragment();
}
