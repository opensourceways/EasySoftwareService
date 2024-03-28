package com.easysoftware.redis;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;  
import java.util.List;
import java.util.Map;

import org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi.ecCVCDSA;
import com.fasterxml.jackson.databind.ObjectMapper; 
import java.nio.charset.StandardCharsets;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.math.BigInteger;  

public class RedisUtil {
     public static String objectToString(Object obj) {  
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
                e.printStackTrace();  
            }  
        }  
  
        // 移除最后的逗号  
        if (sb.length() > 0) {  
            sb.setLength(sb.length() - 2);  
        }  
  
        return sb.toString();  
    }  
  
    private static List<Field> getAllFields(Class<?> clazz) {  
        List<Field> fieldList = new ArrayList<>();  
        Class<?> superClass = clazz.getSuperclass();  
        if (superClass != null) {  
            fieldList.addAll(getAllFields(superClass));  
        }  
        Field[] declaredFields = clazz.getDeclaredFields();  
        for (Field field : declaredFields) {  
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&  
                !java.lang.reflect.Modifier.isTransient(field.getModifiers())) {  
                fieldList.add(field);  
            }  
        }  
        return fieldList;  
    }  

    public static Object convertToObject(String json) {  
        try {  
            // 创建ObjectMapper实例  
            ObjectMapper objectMapper = new ObjectMapper();  
  
            // 将JSON字符串转换为JsonResponse对象  
            JasonResponse jsonResponse = objectMapper.readValue(json, JasonResponse.class);
              
            return jsonResponse;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null; 
        }  
    }

    public static String convertToJson(Map res) {  
        try {  
            // 创建ObjectMapper实例  
            ObjectMapper objectMapper = new ObjectMapper();  
            // 序列化为JSON字符串  
            String json = objectMapper.writeValueAsString(res);  
              
            return json;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return null; 
        }  
    }
    public static String getSHA256(String input) {  
        try {  
            // 初始化MessageDigest实例，并指定SHA-256算法  
            MessageDigest md = MessageDigest.getInstance("SHA-256");  
              
            // 将输入字符串转换为字节数组，并更新摘要  
            md.update(input.getBytes(StandardCharsets.UTF_8));  
              
            // 完成哈希计算，并获取哈希值的字节表示  
            byte[] hash = md.digest();  
              
            // 将字节数组转换为十六进制字符串  
            StringBuilder hexString = new StringBuilder();  
            for (byte b : hash) {  
                String hex = Integer.toHexString(0xff & b);  
                if (hex.length() == 1) hexString.append('0');  
                hexString.append(hex);  
            }  
              
            return hexString.toString();  
        } catch (NoSuchAlgorithmException e) {  
            throw new RuntimeException(e);   
        }  
    }  
}

