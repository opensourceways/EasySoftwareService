package com.easysoftware.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestControllerAdvice()
public class GlobalExceptionHandler {
    private static final Logger logger =  LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(MethodArgumentNotValidException e) {
        System.out.println(e);
        logger.error(MessageCode.EC0002.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    @ExceptionHandler(EnumValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(EnumValidException e) {
        logger.error(MessageCode.EC0002.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
    }

    @ExceptionHandler(AppPkgIconException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> exception(AppPkgIconException e) {
        logger.error(MessageCode.EC0009.getMsgEn());
        return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
    }
}
