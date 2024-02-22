package com.easysoftware.adapter;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MyErrorController implements ErrorController{
    private final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<Object> errorHtml(HttpServletRequest request, HttpServletResponse response) {
        return ResultUtil.fail(HttpStatus.NOT_FOUND, MessageCode.EC0001);
    }
}

