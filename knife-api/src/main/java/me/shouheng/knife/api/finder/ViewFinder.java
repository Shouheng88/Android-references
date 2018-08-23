package me.shouheng.knife.api.finder;

import android.content.Context;
import android.view.View;

/**
 * @author shouh
 * @version $Id: ViewFinder, v 0.1 2018/8/22 22:45 shouh Exp$
 */
public class ViewFinder implements Finder {

    @Override
    public Context getContext(Object source) {
        return ((View) source).getContext();
    }

    @Override
    public View findView(Object source, int id) {
        return ((View) source).findViewById(id);
    }
}
