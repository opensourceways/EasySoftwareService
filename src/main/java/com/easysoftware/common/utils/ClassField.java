package com.easysoftware.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassField {
    public static List<String> getFieldNames(Object obj) {
        List<String> columnNames = new ArrayList<>();
        
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            if (List.class.isAssignableFrom(field.getType())) {
                continue;
            }
            String fieldName = field.getName();
            String columnName = fieldName.replaceAll("([A-Z])", "_$1").toLowerCase();
            columnNames.add(columnName);
        }
        
        return columnNames;
    }
}
