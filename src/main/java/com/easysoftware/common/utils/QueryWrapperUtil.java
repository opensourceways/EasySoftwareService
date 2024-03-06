package com.easysoftware.common.utils;

import java.lang.reflect.Field;

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
            wrapper.eq(undLine, vStr);
        }
        return wrapper;
    }
}
