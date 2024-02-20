package com.easysoftware.service.impl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.entity.dto.ApplicationPackageSearchCondition;
import com.easysoftware.entity.dto.InputApplicationPackage;
import com.easysoftware.entity.po.pkg.ApplicationPackage;
import com.easysoftware.entity.vo.HttpResult;
import com.easysoftware.mapper.AppPackageMapper;
import com.easysoftware.service.ApplicationPackageService;
import com.easysoftware.util.UuidUtil;

@Service
public class AppPackageServiceImpl implements ApplicationPackageService {
    @Autowired
    private AppPackageMapper appPackageMapper;


    @Override
    public String deleteAppPkg(List<String> names) {
        QueryWrapper<ApplicationPackage> wrapper = new QueryWrapper<>();
   
        wrapper.in("name", names);

        int mark = appPackageMapper.delete(wrapper);
        if (mark != 0) {
            return HttpResult.ok(String.format("需要删除 %d 条数据,完成删除 %d 条数据"
            , names.size(), mark), null);
        }
        return HttpResult.fail(400, "删除失败", null);
    }


    @Override
    public String insertAppPkg(InputApplicationPackage inputApp) {
        // 数据库中是否已存在该包
        boolean found = findAppFromTable(inputApp.getName());
        if (found) {
            return HttpResult.fail(400, "请求的包已存在", null);
        }
        ApplicationPackage appPackage = new ApplicationPackage();
        BeanUtils.copyProperties(inputApp, appPackage);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        appPackage.setCreateAt(currentTime);
        appPackage.setUpdateAt(currentTime);
        appPackage.setId(id);

        int mark = appPackageMapper.insert(appPackage);
        if (mark != 1) {
            return HttpResult.fail(400, "新增数据失败", null);
        }
        return HttpResult.ok("新增1条数据", null);
    }


    @Override
    public String updateAppPkg(InputApplicationPackage inputApp) {
        // 数据库中是否已存在该包
        boolean found = findAppFromTable(inputApp.getName());
        if (!found) {
            return HttpResult.fail(400, "请求的包不存在", null);
        }

        UpdateWrapper<ApplicationPackage> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", inputApp.getName());
        ApplicationPackage appPackage = new ApplicationPackage();
        BeanUtils.copyProperties(inputApp, appPackage);

        int mark = appPackageMapper.update(appPackage, wrapper);
        if (mark != 1) {
            return HttpResult.fail(400, "新增数据失败", null);
        }
        return HttpResult.ok("新增1条数据", null);
    }


    private boolean findAppFromTable(String name) {
        QueryWrapper<ApplicationPackage> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return appPackageMapper.exists(wrapper);
    }


    @Override
    public String searchAppPkg(ApplicationPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String exactSearch = condition.getExactSearch();

        Page<ApplicationPackage> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ApplicationPackage> wrapper = new QueryWrapper<>();

        Map<String, List<String>> conMap = parseCondition(condition);

        // 组装  wrapper
        if ("exact".equals(exactSearch)) {
            for (Map.Entry<String, List<String>> con : conMap.entrySet()) {
                wrapper.in(con.getKey(), con.getValue());
            }
        } else if ("fuzzy".equals(exactSearch)) {
            for (Map.Entry<String, List<String>> con : conMap.entrySet()) {
                List<String> values = con.getValue();
                for (String value : values) {
                    wrapper.like(con.getKey(), value).or();
                } 
            }
        } else {

        }

        Page<ApplicationPackage> resPage = appPackageMapper.selectPage(page, wrapper);
        List<ApplicationPackage> res = resPage.getRecords();
        return HttpResult.ok("查询完成", res);
    }

    private Map<String, List<String>> parseCondition(ApplicationPackageSearchCondition condition) {
        Field[] fields = condition.getClass().getDeclaredFields();
        Map<String, List<String>> res = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);

            String name = field.getName();
            if ("pageNum".equals(name) || "pageSize".equals(name) || "exactSearch".equals(name)) {
                continue;
            }

            String typeName = field.getType().getName();
            if (! "java.lang.String".equals(typeName)) {
                continue;
            }

            String value = "";
            try {
                value = (String) field.get(condition);
            } catch (IllegalAccessException e) {
                continue;
            }

            if (! StringUtils.hasText(value)) {
                continue;
            }

            String[] splits = value.split(",");
                
            List<String> values = new ArrayList<>();
            for (String v : splits) {
                v = v.strip();
                if (StringUtils.hasText(v)) {
                    values.add(v);
                }
            }

            res.put(name, values);

        }
        return res;
    }
}


