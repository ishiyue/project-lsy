package com.lsy.test.system.common.annotation;

import java.lang.annotation.*;

/**
 * @author cyh
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServerLog {
}
