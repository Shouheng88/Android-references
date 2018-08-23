package me.shouheng.knife.api;

/**
 * @author shouh
 * @version $Id: Unbinder, v 0.1 2018/8/22 22:42 shouh Exp$
 */
public interface Unbinder {

    void unbind();

    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {

        }
    };
}
