package me.shouheng.references.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import me.shouheng.references.MyApplication;
import me.shouheng.references.di.module.ActivityModule;
import me.shouheng.references.di.module.AppModule;
import me.shouheng.references.di.module.FragmentModule;
import me.shouheng.references.di.module.ViewModelModule;

/**
 * Will auto-generate {@link DaggerAppComponent} see more details in it.
 *
 * @author shouh
 * @version $Id: AppComponent, v 0.1 2018/6/6 22:34 shouh Exp$
 */
@Singleton
@Component(modules = {ActivityModule.class,
        ViewModelModule.class,
        AppModule.class,
        FragmentModule.class})
public interface AppComponent extends AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance Builder application(Application application);

        AppComponent build();
    }
}
