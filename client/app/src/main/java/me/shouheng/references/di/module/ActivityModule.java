package me.shouheng.references.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.shouheng.references.util.enums.ActivityScoped;
import me.shouheng.references.view.MainActivity;
import me.shouheng.references.view.live.LiveActivity;

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
}
