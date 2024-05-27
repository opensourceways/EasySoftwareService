/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.redis;

import com.easysoftware.common.entity.MessageCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class RedisUtil {


    // Private constructor to prevent instantiation of the MapConstant class
    private RedisUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("RedisUtil class cannot be instantiated.");
    }

    /**
     * Logger instance for RedisUtil class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * Convert an object to its string representation.
     *
     * @param obj The object to convert to a string.
     * @return The string representation of the object.
     */
    public static String objectToString(final Object obj) {
        if (obj == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        Class<?> clazz = obj.getClass();
        List<Field> fields = getAllFields(clazz);

        for (Field field : fields) {
            try {
                field.setAccessible(true); // 允许访问私有字段
                Object value = field.get(obj);
                String fieldName = field.getName();
                String fieldValue = String.valueOf(value);
                sb.append(fieldName).append(":").append(fieldValue).append("_");
            } catch (IllegalAccessException e) {
                LOGGER.error(MessageCode.EC0001.getMsgEn(), e.getMessage());
            }
        }

        // 移除最后的逗号
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }

    /**
     * Get all fields of a class, including inherited fields.
     *
     * @param clazz The class to retrieve fields for.
     * @return List of all fields in the class.
     */
    private static List<Field> getAllFields(final Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            fieldList.addAll(getAllFields(superClass));
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())
                    && !java.lang.reflect.Modifier.isTransient(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        return fieldList;
    }

    /**
     * Convert a JSON string to an Object.
     *
     * @param json The JSON string to convert.
     * @return The Object converted from the JSON string.
     */
    public static Object convertToObject(final String json) {
        try {
            // 创建ObjectMapper实例
            ObjectMapper objectMapper = new ObjectMapper();

            // 将JSON字符串转换为JsonResponse对象

            return objectMapper.readValue(json, JasonResponse.class);
        } catch (IOException e) {
            LOGGER.error(MessageCode.EC0001.getMsgEn(), e.getMessage());
            return null;
        }
    }

    /**
     * Convert a Map to a JSON string.
     *
     * @param res The Map to convert to JSON.
     * @return The JSON string representation of the Map.
     */
    public static String convertToJson(final Map res) {
        try {
            // 创建ObjectMapper实例
            ObjectMapper objectMapper = new ObjectMapper();
            // 序列化为JSON字符串
            String json = objectMapper.writeValueAsString(res);

            return json;
        } catch (IOException e) {
            LOGGER.error(MessageCode.EC0001.getMsgEn(), e.getMessage());
            return null;
        }
    }
}

