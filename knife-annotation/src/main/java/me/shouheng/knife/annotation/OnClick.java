package me.shouheng.knife.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将单击事件与指定的多个id绑定到一起
 *
 * @author shouh
 * @version $Id: OnClick, v 0.1 2018/8/22 22:20 shouh Exp$
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface OnClick {

    /**
     * 与该单击事件进行绑定的多个 id
     *
     * @return 用于绑定的多个 id
     */
    int[] ids();
}
