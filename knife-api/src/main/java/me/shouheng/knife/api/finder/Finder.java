package me.shouheng.knife.api.finder;

import android.content.Context;
import android.view.View;

/**
 * @author shouh
 * @version $Id: Finder, v 0.1 2018/8/22 22:27 shouh Exp$
 */
public interface Finder {

    Context getContext(Object source);

    View findView(Object source, int id);
}
