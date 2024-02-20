package com.easysoftware.application;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easysoftware.domain.common.exception.ParamErrorException;
import com.easysoftware.domain.common.utils.HttpResult;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;


@Slf4j
@RestControllerAdvice()
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        if (!result.hasErrors()) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求错误", null);
        }
        List<ObjectError> errors = result.getAllErrors();
        // ViolationFieldError
        for (ObjectError error : errors) {
            log.error("未通过参数校验: {}", error.toString());
        }
        return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求错误", null);
    }

    
}
