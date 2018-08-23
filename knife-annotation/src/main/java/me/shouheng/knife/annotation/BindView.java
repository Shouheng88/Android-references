package me.shouheng.knife.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来将View与指定的id进行绑定
 *
 * @author shouh
 * @version $Id: BindView, v 0.1 2018/8/22 22:22 shouh Exp$
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface BindView {

    /**
     * 与该View进行绑定的id
     *
     * @return id
     */
    int id();
}
