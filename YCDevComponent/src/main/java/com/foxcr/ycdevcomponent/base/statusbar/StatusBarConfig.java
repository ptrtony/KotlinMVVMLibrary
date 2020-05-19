package com.foxcr.ycdevcomponent.base.statusbar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusBarConfig {
    boolean isDarkFont() default true;
    int backgroundResId() default 0;
    float alpha() default 0f;
}
