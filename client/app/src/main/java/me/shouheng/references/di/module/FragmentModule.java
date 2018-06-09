package me.shouheng.references.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.shouheng.references.view.live.fragment.RoomFragment;

/**
 * @author shouh
 * @version $Id: FragmentModule, v 0.1 2018/6/9 13:45 shouh Exp$
 */
@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract RoomFragment contributeRoomFragment();
}
