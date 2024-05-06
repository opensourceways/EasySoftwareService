package com.easysoftware.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.enumvalid.TimeOrderEnum;
import com.power.common.util.StringUtil;


public class QueryWrapperUtil {
    private static final Logger logger = LoggerFactory.getLogger(QueryWrapperUtil.class);

    public static <T, U> QueryWrapper<T> createQueryWrapper(T t, U u, String updateAt) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        
        Field[] fields = u.getClass().getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);

            Object value = null;
            try {
                value = field.get(u);
            } catch (Exception e) {
                logger.error(MessageCode.EC00011.getMsgEn(), e);
            }
            if (! (value instanceof String)) {
                continue;
            }

            String vStr = (String) value;
            vStr = StringUtils.trimToEmpty(vStr);
            if (StringUtils.isBlank(vStr)) {
                continue;
            }

            if ("timeOrder".equals(field.getName()) && TimeOrderEnum.DESC.getAlias().equals(vStr)) {
                wrapper.orderByDesc(updateAt);
                continue;
            }
            if ("timeOrder".equals(field.getName()) && TimeOrderEnum.ASC.getAlias().equals(vStr)) {
                wrapper.orderByAsc(updateAt);
                continue;
            }

            String undLine = StringUtil.camelToUnderline(field.getName());
            
            //","代表该字段有多个参数
            if (vStr.contains(",")) {
                List<String> items = splitStr(vStr);
                if (items.size() == 0) {
                    continue;
                }
                wrapper.in(undLine, items);
            } else {
                vStr = StringUtils.trimToEmpty(vStr);
                wrapper.eq(undLine, vStr);
            }
            
        }
        return wrapper;
    }

    public static List<String> splitStr(String vStr) {
        List<String> res = new ArrayList<>();

        String[] sps = StringUtils.split(vStr, ",");
        if (sps.length == 0) {
            return res;
        }

        for (String sp : sps) {
            if (StringUtils.isBlank(StringUtils.trimToEmpty(sp))) {
                continue;
            }
            res.add(sp);
        }
        return res;
    }

    public static List<String> sortOsColumn(List<String> colList) {
        Map<Boolean, List<String>> partMap = colList.stream()
                .collect(Collectors.partitioningBy(e -> !e.contains("preview")));
        List<String> con = partMap.get(true);
        List<String> don = partMap.get(false);
        Collections.sort(con, Collections.reverseOrder());
        con.addAll(don);
        return con;
    }
}
