package me.shouheng.knife.compiler;

import com.squareup.javapoet.ClassName;

/**
 * @author shouh
 * @version $Id: TypeUtils, v 0.1 2018/8/22 22:54 shouh Exp$
 */
public class TypeUtils {

    public final static ClassName FINDER = ClassName.get("me.shouheng.knife.api.finder", "Finder");

    public final static ClassName ONCLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");

    public final static ClassName ANDROID_VIEW = ClassName.get("android.view", "View");

    public static final ClassName INJECTOR = ClassName.get("me.shouheng.knife.api", "Injector");
}
