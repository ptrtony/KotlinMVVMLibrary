package com.foxcr.ycdevvm.base;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBind {
    boolean bind() default true;
}
