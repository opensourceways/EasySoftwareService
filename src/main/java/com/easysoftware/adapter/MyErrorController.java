package com.easysoftware.adapter;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.result.MessageCode;
import com.easysoftware.result.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MyErrorController implements ErrorController{
    private final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public ResponseEntity<Object> errorHtml(HttpServletRequest request, HttpServletResponse response) {
        return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0001);
    }
}

