package com.easysoftware.domain.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class HttpResult {
    private Integer status;
    private String msg;
    private Object object;
    private String updataAt;

    public HttpResult(Throwable e) {
        this.status = HttpStatus.BAD_REQUEST.value();
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
        String updataAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
        HttpResult res = new HttpResult(HttpStatus.OK.value(), msg, object, updataAt);
        return ObjectMapperUtil.writeValueAsString(res);
    }

    public static String fail(Integer status, String msg, Object object) {
        String updataAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
        HttpResult res = new HttpResult(status, msg, object, updataAt);
        return ObjectMapperUtil.writeValueAsString(res);
    }
    
}
