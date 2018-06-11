package me.shouheng.references.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.shouheng.references.di.enums.ActivityScoped;
import me.shouheng.references.view.guokr.GuokrNewsActivity;
import me.shouheng.references.view.layout.NavigationActivity;
import me.shouheng.references.view.live.activity.LiveActivity;
import me.shouheng.references.view.live.activity.LiveRoomActivity;
import me.shouheng.references.view.main.MainActivity;

/**
 * @author shouh
 * @version $Id: ActivityModule, v 0.1 2018/6/6 22:43 shouh Exp$
 */
@Module
public abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LiveActivity liveActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LiveRoomActivity liveRoomActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract GuokrNewsActivity guokrNewsActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract NavigationActivity navigationActivity();
}
