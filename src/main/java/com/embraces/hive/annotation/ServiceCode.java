package com.embraces.hive.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * 标识逻辑层标识
 * @author lijl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface ServiceCode {
    String value();
}
