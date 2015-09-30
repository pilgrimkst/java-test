package com.javatest.refactor.dao.config.cache;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class PersonKeyGenerator implements KeyGenerator {

    private static final int NO_PARAM_KEY = 0;
    private static final int NULL_PARAM_KEY = 53;

    public Object generate(Object target, Method method, Object... params) {

        if (params.length == 1) {
            return (params[0] == null ? NULL_PARAM_KEY : params[0].hashCode());
        }

        if (params.length == 0) {
            return NO_PARAM_KEY;
        }

        int hashCode = 17;
        for (Object object : params) {
            hashCode = 31 * hashCode + (object == null ? NULL_PARAM_KEY : object.hashCode());
        }
        return hashCode;
    }

}
