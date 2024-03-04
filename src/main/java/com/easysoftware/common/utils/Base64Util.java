package com.easysoftware.common.utils;

import java.lang.reflect.Field;
import java.util.Base64;

import com.easysoftware.common.exception.Base64Exception;

public class Base64Util {
    public static <T> T decode(T obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
            Object value = field.get(obj);
            if (!(value instanceof String)) {
                continue;
            }
            String base64Value = (String) value;
            String decodeValue = new String(Base64.getDecoder().decode(base64Value));
            field.set(obj, decodeValue);
            field.setAccessible(false);
            } catch (Exception e) {
                throw new Base64Exception();
            }
        }
        return obj;
    } 
}
