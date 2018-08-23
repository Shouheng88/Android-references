package me.shouheng.knife.api;

import android.app.Activity;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import me.shouheng.knife.api.finder.ActivityFinder;
import me.shouheng.knife.api.finder.Finder;
import me.shouheng.knife.api.finder.ViewFinder;

/**
 * @author shouh
 * @version $Id: MyKnife, v 0.1 2018/8/22 22:50 shouh Exp$
 */
public class MyKnife {

    public MyKnife() {
        throw new AssertionError("Not available for instance");
    }

    private final static ActivityFinder ACTIVITY_FINDER = new ActivityFinder();

    private final static ViewFinder VIEW_FINDER = new ViewFinder();

    private final static Map<String, Injector> FINDER_MAPPER = new HashMap<>();

    public static void bind(Activity activity) {
        bind(activity, activity, ACTIVITY_FINDER);
    }

    public static void bind(View view) {
        bind(view, view);
    }

    public static void bind(Object host, View view) {
        bind(host, view, VIEW_FINDER);
    }

    public static void bind(Object host, Object source, Finder finder) {
        String className = host.getClass().getName();
        try {
            Injector injector = FINDER_MAPPER.get(className);
            if (injector == null) {
                Class<?> finderClass = Class.forName(className + "$$Injector");
                injector = (Injector) finderClass.newInstance();
                FINDER_MAPPER.put(className, injector);
            }
            injector.inject(host, source, finder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
