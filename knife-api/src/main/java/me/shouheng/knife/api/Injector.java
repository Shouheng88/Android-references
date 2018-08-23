package me.shouheng.knife.api;

import me.shouheng.knife.api.finder.Finder;

/**
 * @author shouh
 * @version $Id: Injector, v 0.1 2018/8/22 22:41 shouh Exp$
 */
public interface Injector<T> {

    void inject(T host, Object source, Finder finder);
}
