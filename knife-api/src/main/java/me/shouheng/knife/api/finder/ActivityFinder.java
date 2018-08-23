package me.shouheng.knife.api.finder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * @author shouh
 * @version $Id: ActivityFinder, v 0.1 2018/8/22 22:34 shouh Exp$
 */
public class ActivityFinder implements Finder {

    @Override
    public Context getContext(Object source) {
        return (Activity) source;
    }

    @Override
    public View findView(Object source, int id) {
        return ((Activity) source).findViewById(id);
    }
}
