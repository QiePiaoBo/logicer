package com.dylan.commserver.anno;


import com.dylan.commserver.config.MyCoreConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyService {

    String value() default MyCoreConfig.NULL_SERVICE_NAME;

}
