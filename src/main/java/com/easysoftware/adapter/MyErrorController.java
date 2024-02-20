package com.easysoftware.adapter;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.domain.common.utils.HttpResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MyErrorController implements ErrorController{
    private final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String errorHtml(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        return HttpResult.fail(status, "error uri", "");
    }
}

