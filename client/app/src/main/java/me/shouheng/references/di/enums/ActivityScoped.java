package me.shouheng.references.di.enums;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author shouh
 * @version $Id: ActivityScoped, v 0.1 2018/6/6 22:48 shouh Exp$
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped { }
