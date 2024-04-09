package com.easysoftware.common.utils;

import java.lang.reflect.Field;
import java.util.Map;

import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;

public class ParseResultUtil {
    public static Object parseData(String content) {
        Map<String, Object> map = ObjectMapperUtil.toMap(content);
        Object data =  map.get("data");
        return data;
    }

    public static <T> boolean compare(Map<String, String> rpmMap, T t) throws Exception {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String filedName = field.getName();
            Object value = field.get(t);
            Object mapValue = rpmMap.get(filedName);

            if (value == null && mapValue == null) {
                continue;
            } else if ((value == null && mapValue != null) || (value != null && mapValue == null)) {
                return false;
            } else if (value != null && mapValue != null) {
                String valueS = (String) value;
                String mapValueS = (String) mapValue;
                if (! valueS.equals(mapValueS)) {
                    return false;
                }
            } else {
            }
        }
        return true;
    }

}
