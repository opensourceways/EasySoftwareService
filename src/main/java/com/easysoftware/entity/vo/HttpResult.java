package com.easysoftware.entity.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.easysoftware.util.ObjectMapperUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class HttpResult {
    private Integer status;
    private String msg;
    private Object object;
    private String updataAt;

    public HttpResult(Throwable e) {
        this.status = 201;
        this.msg = e.getMessage();
    }

    public HttpResult() {
    }

    public HttpResult(Integer status, String msg, Object object, String updataAt) {
        this.status = status;
        this.msg = msg;
        this.object = object;
        this.updataAt = updataAt;
    }

    public static String ok(String msg, Object object) {
        HttpResult res = new HttpResult(200, msg, object, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
        return ObjectMapperUtil.writeValueAsString(res);
    }

    public static String fail(Integer status, String msg, Object object) {
        HttpResult res = new HttpResult(status, msg, object, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date()));
        return ObjectMapperUtil.writeValueAsString(res);
    }
    
}
